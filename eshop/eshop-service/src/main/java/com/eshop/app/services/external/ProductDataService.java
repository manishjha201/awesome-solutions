package com.eshop.app.services.external;

import com.eshop.app.client.ProductClient;
import com.eshop.app.client.TenantClient;
import com.eshop.app.common.constants.ChangeType;
import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.constants.FormType;
import com.eshop.app.common.entities.rdbms.User;
import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.common.models.ProductChangeEvent;
import com.eshop.app.common.models.ProductChangeMetaData;
import com.eshop.app.common.models.kafka.EShoppingChangeEventKafka;
import com.eshop.app.common.models.kafka.Product;
import com.eshop.app.intercepters.UserContext;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.ProductResp;
import com.eshop.app.producer.IEshopChangeEventPublisherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
@Slf4j
public class ProductDataService implements IProductDataService {

    private static final Gson gson = new Gson();

    @Autowired
    private ProductClient clientService;

    @Autowired
    private TenantClient tenantClient;

    @Autowired
    private Executor executor;

    @Autowired
    private IEshopChangeEventPublisherService eshopChangeEventPublisherService;

    @Override
    public ProductResp getProduct(Long productId, String loginId, String esToken) throws JsonProcessingException {
        return clientService.getProduct(productId, loginId, esToken);
    }

    @Override
    public ProductResp createProduct(ProductReqDTO createProductReq, String loginId, String esToken) throws JsonProcessingException {
        User user = UserContext.getUserDetail();
        ProductResp resp = clientService.createProduct(createProductReq, loginId, esToken);
        publishChangeEvent(null, resp.getProduct(), user, resp.getProduct().getTenantId(), ChangeType.CREATE, FormType.ESHOPPING, loginId, esToken);
        return resp;
    }

    @Override
    public ProductResp updateProduct(Long productId, ProductUpdateReqDTO updateProductReq, String loginId, String esToken) throws JsonProcessingException {
        User user = UserContext.getUserDetail();
        ProductResp from  = getProduct(productId, loginId, esToken);
        ProductResp resp = clientService.updateProduct(productId, updateProductReq, loginId, esToken);
        publishChangeEvent(from.getProduct(), resp.getProduct(), user, resp.getProduct().getTenantId(), ChangeType.CREATE, FormType.ESHOPPING, loginId, esToken);
        return resp;
    }

    public void publishChangeEvent(Product from, Product to, User user, Long tenantId, ChangeType changeType, FormType formType, String loginId, String token) {
        executor.execute( () -> {
            try {
                publish(from, to, user, tenantId, changeType, formType, loginId, token);
            } catch (JsonProcessingException e) {
                throw new BusinessException(EShopResultCode.SYSTEM_ERROR.getResultCode() + e.getMessage());
            }
        });
    }

    public void publish(final Product from, final Product to, final User updatedBy, Long tenantId, ChangeType changeType, FormType formType, String loginId, String token) throws JsonProcessingException {
        com.eshop.app.common.models.kafka.Tenant tenant = tenantClient.getTenant(tenantId, loginId, token).getTenant();
        //TODO : GET TimeZone and TimeAt from controller
        String key = to.getId().toString();
        ProductChangeMetaData metadata = ProductChangeMetaData.builder().changeType(changeType.name()).formType(formType.name())
                .tenant(tenant).user(updatedBy.build()).build();
        EShoppingChangeEvent changeEvent = EShoppingChangeEvent.builder()
                .productChangeEvent(ProductChangeEvent.builder().previousValue(from)
                        .currentValue(to).build()).productChangeMetaData(metadata).build();;
        EShoppingChangeEventKafka event = EShoppingChangeEventKafka.builder().dataschema(EShoppingChangeEvent.class.getName()).data(gson.toJson(changeEvent)).build();
        eshopChangeEventPublisherService.publish(key, event);
    }
}
