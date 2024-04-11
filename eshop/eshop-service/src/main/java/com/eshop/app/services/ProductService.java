package com.eshop.app.services;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.entities.rdbms.Tenant;
import com.eshop.app.common.models.ProductChangeMetaData;
import com.eshop.app.common.repositories.rdbms.slave.TenantRepository;
import com.eshop.app.exception.ValidationException;
import com.eshop.app.intercepters.UserContext;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.producer.IEshopChangeEventPublisherService;
import com.eshop.app.utils.Utility;
import com.google.gson.Gson;
import com.eshop.app.common.constants.ChangeType;
import com.eshop.app.common.constants.FormType;
import com.eshop.app.common.constants.Status;
import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.common.models.ProductChangeEvent;
import com.eshop.app.common.models.kafka.EShoppingChangeEventKafka;
import com.eshop.app.exception.ResourceNotFoundException;
import com.eshop.app.mapper.req.RequestMapper;
import com.eshop.app.mapper.resp.ResponseMapper;
import com.eshop.app.models.req.GetProductsRequestDto;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.resp.ProductDetailResponse;
import com.eshop.app.models.resp.ProductsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;

import com.eshop.app.common.entities.rdbms.User;

@Service
@Slf4j
public class ProductService implements IProductService {

    private static final Gson gson = new Gson();

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    @PersistenceContext(unitName = "master")
    private EntityManager masterEntityManager;

    @Autowired
    @PersistenceContext(unitName = "slave")
    private EntityManager slaveEntityManager;

    @Autowired
    private Executor executor;

    @Autowired
    private IEshopChangeEventPublisherService eshopChangeEventPublisherService;

    @Transactional(value = "eShopMasterTransactionManager", readOnly = false)
    @Override
    public ProductDetailResponse createProduct(ProductReqDTO createProductReq, String loginId) {
        User user = UserContext.getUserDetail();
        Product product = RequestMapper.buildProductFromProductReqDTO(createProductReq, user);
        masterEntityManager.persist(product);
        masterEntityManager.flush();
        ProductDetailResponse resp = ResponseMapper.mapProductDetailResponse(product);
        publishChangeEvent(null, product, user, resp.getResponse().getTenantId(), ChangeType.CREATE, FormType.ESHOPPING);
        return resp;
    }

    public void publishChangeEvent(Product from, Product to, User user, Long tenantId, ChangeType changeType, FormType formType) {
       executor.execute( () -> publish(from, to, user, tenantId, changeType, formType));
    }

    public void publish(final Product from, final Product to, final User updatedBy, Long tenantId, ChangeType changeType, FormType formType) {
        //TODO : Use Caching
        Tenant tenant = getTenantById(tenantId);
        //TODO : GET TimeZone and TimeAt from controller
        String key = to.getId().toString();
        ProductChangeMetaData metadata = ProductChangeMetaData.builder().changeType(changeType.name()).formType(formType.name())
                .tenant(tenant.build()).user(updatedBy.build()).build();
        EShoppingChangeEvent changeEvent = EShoppingChangeEvent.builder()
                .productChangeEvent(ProductChangeEvent.builder().previousValue(Utility.parseToKafkaProduct(from))
                        .currentValue(Utility.parseToKafkaProduct(to)).build()).productChangeMetaData(metadata).build();;
        EShoppingChangeEventKafka event = EShoppingChangeEventKafka.builder().dataschema(EShoppingChangeEvent.class.getName()).data(gson.toJson(changeEvent)).build();
        eshopChangeEventPublisherService.publish(key, event);
    }

    @Transactional(value = "eShopSlaveTransactionManager", readOnly = true)
    @Override
    public ProductsResponse getProducts(GetProductsRequestDto dto, String loginId) throws ResourceNotFoundException {
        CriteriaBuilder cb = slaveEntityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> product = cq.from(Product.class);
        List<Long> ids = dto.getProductIds();
        cq.select(product)
                .where(cb.notEqual(product.get("status"), Status.DELETED),
                product.get("id").in(ids));
        List<Product> products = slaveEntityManager.createQuery(cq).getResultList();
        return ResponseMapper.mapGetProductDetailResponse(products);
    }


    @Transactional(value = "eShopMasterTransactionManager", readOnly = false)
    @Override
    public ProductDetailResponse updateProduct(Long productId, ProductUpdateReqDTO updateReq, String loginId) {
        User user = UserContext.getUserDetail();
        //TODO : Use Cache to fetch Cached Product Detail.....
        Product from = masterEntityManager.find(Product.class, productId);
        Product product = RequestMapper.updateProductFromProductReqDTO(updateReq, user);
        if (from.getVersion() != product.getVersion()) throw new ValidationException(EShopResultCode.INVALID_INPUT, "invalid version number");
        product.setId(productId);
        product.setVersion(from.getVersion() + 1);
        Product updatedProduct = masterEntityManager.merge(product);
        masterEntityManager.flush();
        ProductDetailResponse resp = ResponseMapper.mapProductDetailResponse(updatedProduct);
        publishChangeEvent(from, updatedProduct, user, updatedProduct.getTenantId(), ChangeType.UPDATE, FormType.ESHOPPING);
        return resp;
    }

    @Transactional(value = "eShopMasterTransactionManager", readOnly = false)
    @Override
    public void deleteProduct(List<Long> productIds, String loginId) throws ResourceNotFoundException {
        User user = UserContext.getUserDetail();
        for (Long productId : productIds) {
            Product product = masterEntityManager.find(Product.class, productId);
            if (product != null) {
                masterEntityManager.remove(product);
                publishChangeEvent(product, null, user, product.getTenantId(), ChangeType.DELETE, FormType.ESHOPPING);
            } else {
                log.info("Product with ID {} not found.", productId );
            }
        }
    }

    @Transactional(value = "eShopSlaveTransactionManager", readOnly = true)
    public Tenant getTenantById(Long tenantId) {
        Optional<Tenant> tenantOptional = tenantRepository.findById(tenantId);
        if (tenantOptional.isPresent()) {
            return tenantOptional.get();
        }
        return Tenant.builder().id(0L).name("NA").status(Status.ACTIVE).version(1).build();
    }

    @Transactional("eShopMasterTransactionManager")
    public Product patchProduct(Long id, Map<String, Object> updates) {
        Product product = slaveEntityManager.find(Product.class, id);
        if (product != null) {
            updates.forEach((key, value) -> {
                switch (key) {
                    case "name": product.setName((String) value); break;
                    case "price": product.setPrice((BigDecimal) value); break;
                }
            });
            masterEntityManager.merge(product);
        }
        return product;
    }
}
