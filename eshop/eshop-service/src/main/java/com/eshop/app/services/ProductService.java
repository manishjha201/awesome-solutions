package com.eshop.app.services;

import com.eshop.app.client.TenantClient;
import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.entities.rdbms.Tenant;
import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.exception.ValidationException;
import com.eshop.app.intercepters.UserContext;
import com.eshop.app.models.req.ProductRequest;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.ProductResp;
import com.eshop.app.common.constants.Status;
import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.exception.ResourceNotFoundException;
import com.eshop.app.mapper.req.RequestMapper;
import com.eshop.app.mapper.resp.ResponseMapper;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.resp.TenantResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.eshop.app.common.entities.rdbms.User;

@Service
@Slf4j
public class ProductService implements IProductService {

    @Autowired
    @PersistenceContext(unitName = "master")
    private EntityManager masterEntityManager;

    @Autowired
    @PersistenceContext(unitName = "slave")
    private EntityManager slaveEntityManager;

    @Autowired
    private TenantClient tenantClient;

    @Transactional(value = "eShopMasterTransactionManager", readOnly = false)
    @Override
    public ProductResp createProduct(ProductReqDTO createProductReq, String loginId, String token) {
        User user = UserContext.getUserDetail();
        Product product = RequestMapper.buildProductFromProductReqDTO(createProductReq, user);
        masterEntityManager.persist(product);
        masterEntityManager.flush();
        ProductResp resp = ResponseMapper.mapProductDetailResponse(product);
        return resp;
    }

    @Transactional(value = "eShopSlaveTransactionManager", readOnly = true)
    @Override
    public ProductResp getProduct(ProductRequest dto, String loginId) throws ResourceNotFoundException {
        CriteriaBuilder cb = slaveEntityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> product = cq.from(Product.class);
        List<Long> ids = List.of(dto.getProductId());
        cq.select(product)
                .where(
                        cb.and(
                                cb.notEqual(product.get("status"), Status.DELETED),
                                cb.notEqual(product.get("status"), Status.INACTIVE),
                                product.get("id").in(ids)
                        )
                );
        List<Product> products = slaveEntityManager.createQuery(cq).getResultList();
        return ResponseMapper.mapGetProductDetailResponse(products);
    }


    @Transactional(value = "eShopMasterTransactionManager", readOnly = false)
    @Override
    public ProductResp updateProduct(Long productId, ProductUpdateReqDTO updateReq, String loginId, String token) {
        User user = UserContext.getUserDetail();
        Product from = masterEntityManager.find(Product.class, productId);
        Product product = RequestMapper.updateProductFromProductReqDTO(productId, updateReq, user);
        if (from.getVersion() != product.getVersion()) throw new ValidationException(EShopResultCode.INVALID_INPUT, "invalid version number");
        product.setId(productId);
        product.setVersion(from.getVersion() + 1);
        Product updatedProduct = masterEntityManager.merge(product);
        masterEntityManager.flush();
        ProductResp resp = ResponseMapper.mapProductDetailResponse(updatedProduct);
        return resp;
    }

    @Transactional(value = "eShopMasterTransactionManager", readOnly = false)
    @Override
    public void deleteProduct(List<Long> productIds, String loginId, String token) throws ResourceNotFoundException {
        User user = UserContext.getUserDetail();
        for (Long productId : productIds) {
            Product product = masterEntityManager.find(Product.class, productId);
            if (product != null) {
                masterEntityManager.remove(product);
               // publishChangeEvent(product, null, user, product.getTenantId(), ChangeType.DELETE, FormType.ESHOPPING, loginId, token);
            } else {
                log.info("Product with ID {} not found.", productId );
            }
        }
    }

    @Transactional(value = "eShopSlaveTransactionManager", readOnly = true)
    public com.eshop.app.common.models.kafka.Tenant getTenantById(Long tenantId, String loginId, String token) throws JsonProcessingException {
        TenantResp tenantOptional = tenantClient.getTenant(tenantId, loginId, token); //READING FROM CACHE
        if (ObjectUtils.isNotEmpty(tenantOptional)) {
            return tenantOptional.getTenant();
        }
        throw new BusinessException(EShopResultCode.NOT_FOUND.getResultCode());
    }

    //TODO : remove
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
