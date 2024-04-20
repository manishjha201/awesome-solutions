package com.eshop.app.services;

import com.eshop.app.exception.ResourceNotFoundException;
import java.util.List;

import com.eshop.app.models.req.ProductRequest;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.ProductResp;

public interface IProductService {
    ProductResp createProduct(ProductReqDTO createProductReq, String loginId, String token);
    ProductResp getProduct(ProductRequest dto, String loginId) throws ResourceNotFoundException;
    ProductResp updateProduct(Long productId, ProductUpdateReqDTO createProductReq, String loginId, String token);
    //Product patchProduct(Long productId, JsonPatch patch); //TODO : pending
    void deleteProduct(List<Long> productIds, String loginId, String token) throws ResourceNotFoundException;
}
