package com.eshop.app.services.external;

import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.ProductResp;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IProductDataService {
    ProductResp getProduct(Long productId, String loginId, String esToken) throws JsonProcessingException;
    ProductResp createProduct(ProductReqDTO createProductReq, String loginId, String esToken) throws JsonProcessingException;
    ProductResp updateProduct(Long productId, ProductUpdateReqDTO createProductReq, String loginId, String esToken) throws JsonProcessingException;
}
