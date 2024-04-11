package com.eshop.app.services;

import com.eshop.app.exception.ResourceNotFoundException;
import java.util.List;

import com.eshop.app.models.req.GetProductsRequestDto;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.resp.ProductDetailResponse;
import com.eshop.app.models.resp.ProductsResponse;

public interface IProductService {
    ProductDetailResponse createProduct(ProductReqDTO createProductReq, String loginId);
    ProductsResponse getProducts(GetProductsRequestDto dto, String loginId) throws ResourceNotFoundException;
    ProductDetailResponse updateProduct(Long productId, ProductReqDTO createProductReq, String loginId);
    //Product patchProduct(Long productId, JsonPatch patch); //TODO : pending
    void deleteProduct(List<Long> productIds, String loginId) throws ResourceNotFoundException;
}
