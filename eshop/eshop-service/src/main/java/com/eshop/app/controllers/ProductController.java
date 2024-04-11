package com.eshop.app.controllers;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.models.req.GetProductsRequestDto;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.GenericResponseBody;
import com.eshop.app.models.resp.ProductDetailResponse;
import com.eshop.app.models.resp.ProductsResponse;
import com.eshop.app.models.resp.ResultInfo;
import com.eshop.app.services.IProductService;
import com.eshop.app.services.IValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/api/v1/products")
@Validated
@Slf4j
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IValidationService validationService;

    @PostMapping
    public ResponseEntity<GenericResponseBody<ProductDetailResponse>> createProduct(@RequestHeader(value = "loginId", required = false) String loginId, @Valid @RequestBody ProductReqDTO createProductReq) {
        validationService.validate(createProductReq);
        log.info("Request received for API={} with values : {}", "CREATE_PRODUCT_API", createProductReq);
        ProductDetailResponse resp = productService.createProduct(createProductReq, loginId);
        GenericResponseBody<ProductDetailResponse> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
    }

    /**
     * Get list of products.
     * @param productIds
     * @param loginId
     * @return
     */
    //TODO : add swagger
    @GetMapping("{productIds}")
    public ResponseEntity<GenericResponseBody<ProductsResponse>> getProducts(@PathVariable String productIds, @RequestHeader(value = "loginId", required = false) String loginId) {
        GetProductsRequestDto httpReq = GetProductsRequestDto.builder()
                .productIds(Arrays.stream(productIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList()))
                .build();
        validationService.validate(httpReq);
        ProductsResponse resp = productService.getProducts(httpReq, loginId);
        GenericResponseBody<ProductsResponse> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<GenericResponseBody<ProductDetailResponse>> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductUpdateReqDTO updateProductReq, @RequestHeader(value = "loginId", required = false) String loginId) {
        validationService.validate(updateProductReq);
        log.info("Request received for API={} with values : {}", "UPDATE_PRODUCT_API", updateProductReq);
        ProductDetailResponse resp = productService.updateProduct(productId, updateProductReq, loginId);
        GenericResponseBody<ProductDetailResponse> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
    }

    /**
     * Avoid physical delete.
     * Update status to DELETED / INACTIVE.
     * @param productIds
     * @param loginId
     * @return
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable List<Long> productIds, @RequestHeader(value = "loginId", required = false) String loginId) {
        productService.deleteProduct(productIds, loginId);
        return ResponseEntity.noContent().build();
    }

}
