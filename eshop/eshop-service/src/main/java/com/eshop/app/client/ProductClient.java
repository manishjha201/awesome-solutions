package com.eshop.app.client;

import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.ProductResp;
import com.eshop.app.services.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import java.util.Collections;

@Slf4j
@Component
public class ProductClient implements IProductClient {

    @Autowired
    @Qualifier("productClientService")
    private DataService<ProductResp,  ProductResp> productClientService;

    @Override
    public ProductResp getProduct(Long productId, String loginId, String esToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("estoken", esToken);
        headers.add("loginId", loginId);
        //in seconds
        long ttl = 60 * 60;
        ProductResp resp = productClientService.getData("eshop:catalog:product", productId.toString(), ProductResp.class, Collections.emptyMap(), headers, ttl);
        log.info("data received : {} ", resp);
        return resp;
    }

    @Override
    public ProductResp createProduct(ProductReqDTO createProductReq, String loginId, String esToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("estoken", esToken);
        headers.add("loginId", loginId);
        ProductResp resp = productClientService.createData(createProductReq, ProductResp.class, Collections.emptyMap(), headers);
        log.info("data received : {} ", resp);
        return resp;
    }

    @Override
    public ProductResp updateProduct(Long productId, ProductUpdateReqDTO createProductReq, String loginId, String esToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("estoken", esToken);
        headers.add("loginId", loginId);
        ProductResp resp = productClientService.updateData("eshop:catalog:product", productId.toString(), createProductReq, ProductResp.class, Collections.emptyMap(), headers);
        log.info("data received : {} ", resp);
        return resp;
    }

}
