package com.eshop.app.controllers.internal;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.req.ProductRequest;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.*;
import com.eshop.app.services.IProductService;
import com.eshop.app.services.IValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("internal/data/v1/products")
@Validated
@Slf4j
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IValidationService validationService;

    /**
     * Get product from db.
     * @param productId
     * @param loginId
     * @return
     */
    //TODO : add swagger
    @GetMapping("{productId}")
    public ResponseEntity<GenericResponseBody<ProductResp>> getProducts(@PathVariable Long productId, @RequestHeader(value = "loginId", required = false) String loginId) {
        ProductRequest httpReq = ProductRequest.builder()
                .productId(productId)
                .build();
        validationService.validate(httpReq);
        ProductResp resp = productService.getProduct(httpReq, loginId);
        GenericResponseBody<ProductResp> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<GenericResponseBody<ProductResp>> createProduct(
            @RequestHeader(value = "loginId", required = false) String loginId,
            @RequestHeader(value = "estoken", required = false) String esToken,
            @Valid @RequestBody ProductReqDTO createProductReq) {
        validationService.validate(createProductReq);
        log.info("Request received for API={} with values : {} by user : {} ", "CREATE_PRODUCT_API", createProductReq, loginId);
        ProductResp resp = productService.createProduct(createProductReq, loginId, esToken);
        GenericResponseBody< ProductResp> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<GenericResponseBody<ProductResp>> updateProduct(
            @PathVariable Long productId, @Valid @RequestBody ProductUpdateReqDTO updateProductReq,
            @RequestHeader(value = "loginId", required = false) String loginId,
            @RequestHeader(value = "estoken", required = false) String esToken) {
        validationService.validate(updateProductReq);
        log.info("Request received for API={} with values : {}", "UPDATE_PRODUCT_API", updateProductReq);
        ProductResp resp = productService.updateProduct(productId, updateProductReq, loginId, esToken);
        GenericResponseBody< ProductResp> body = new GenericResponseBody<>();
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
    public ResponseEntity<Void> deleteProduct(
            @PathVariable List<Long> productIds,
            @RequestHeader(value = "loginId", required = false) String loginId,
            @RequestHeader(value = "estoken", required = false) String esToken) {
        productService.deleteProduct(productIds, loginId, esToken);
        return ResponseEntity.noContent().build();
    }

}
