package com.eshop.app.services;

import com.eshop.app.common.constants.Role;
import com.eshop.app.common.constants.Status;
import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.exception.ResourceNotFoundException;
import com.eshop.app.mapper.req.RequestMapper;
import com.eshop.app.mapper.resp.ResponseMapper;
import com.eshop.app.models.req.GetProductsRequestDto;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.resp.ProductDetailResponse;
import com.eshop.app.models.resp.ProductsResponse;
import com.github.fge.jsonpatch.JsonPatch;
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

    @Transactional(value = "eShopMasterTransactionManager", readOnly = false)
    @Override
    public ProductDetailResponse createProduct(ProductReqDTO createProductReq, String loginId) {
        //TODO : Use Cache to fetch User Detail.....
        User test = User.builder().id(1L).username("TEST").password("fdasfdsf").name("test").email("email@test").loginId(loginId).role(Role.ADMIN).tenantId(1L).isActive(true).version(1).build();
        Product product = RequestMapper.buildProductFromProductReqDTO(createProductReq, test);
        masterEntityManager.persist(product);
        masterEntityManager.flush();
        ProductDetailResponse resp = ResponseMapper.mapProductDetailResponse(product);
        //TODO : SEND MESSAGE TO KAFKA
        return resp;
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
    public ProductDetailResponse updateProduct(Long productId, ProductReqDTO createProductReq, String loginId) {
        //TODO : Use Cache to fetch User Detail.....
        User test = User.builder().id(1L).username("TEST").password("fdasfdsf").name("test").email("email@test").loginId(loginId).role(Role.ADMIN).tenantId(1L).isActive(true).version(1).build();
        Product product = RequestMapper.buildProductFromProductReqDTO(createProductReq, test);
        product.setId(productId);
        Product updatedProduct = masterEntityManager.merge(product);
        masterEntityManager.flush();
        ProductDetailResponse resp = ResponseMapper.mapProductDetailResponse(updatedProduct);
        //TODO : SEND MESSAGE TO KAFKA
        return resp;
    }

    @Transactional(value = "eShopMasterTransactionManager", readOnly = false)
    @Override
    public void deleteProduct(List<Long> productIds, String loginId) throws ResourceNotFoundException {
        for (Long productId : productIds) {
            Product product = masterEntityManager.find(Product.class, productId);
            if (product != null) {
                masterEntityManager.remove(product);
            } else {
                log.info("Product with ID {} not found.", productId );
            }
        }
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
