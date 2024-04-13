package com.eshop.app.controllers.external;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.GenericResponseBody;
import com.eshop.app.models.resp.ProductResp;
import com.eshop.app.models.resp.ResultInfo;
import com.eshop.app.services.IValidationService;
import com.eshop.app.services.external.IProductDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("")
@Validated
@Slf4j
public class ProductDataServiceController {

    @Autowired
    private IValidationService validationService;

    @Autowired
    private IProductDataService productDataService;

    /**
     * Get product from data service which used internal api and lookup service to product fetch data.
     * First lookup in cache and then if a miss fetch from db.
     * @param productId
     * @param loginId
     * @return
     */
    //TODO : add swagger
    @GetMapping("user/api/v1/products/{productId}")
    public ResponseEntity<GenericResponseBody<ProductResp>> getProductInfo(@PathVariable Long productId, @RequestHeader(value = "loginId", required = false) String loginId, @RequestHeader(value = "estoken", required = true) String estoken) throws JsonProcessingException {
        validationService.validateToken(estoken);
        ProductResp resp = productDataService.getProduct(productId, loginId, estoken);
        GenericResponseBody<ProductResp> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    //TODO : add swagger
    @PostMapping("admin/api/v1/products")
    public ResponseEntity<GenericResponseBody<ProductResp>> createProduct(
            @RequestHeader(value = "loginId", required = false) String loginId,
            @RequestHeader(value = "estoken", required = false) String esToken,
            @Valid @RequestBody ProductReqDTO createProductReq) throws JsonProcessingException {
        validationService.validate(createProductReq);
        log.info("Request received for API={} with values : {} by user : {} ", "CREATE_PRODUCT_API", createProductReq, loginId);
        ProductResp resp = productDataService.createProduct(createProductReq, loginId, esToken);
        GenericResponseBody<ProductResp> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    /***
     * Updates Product data.
     * Version number must be same.
     * Also use this fpr product delete make status as  DELETED
     */
    //TODO : add swagger
    @PutMapping("admin/api/v1/products/{productId}")
    public ResponseEntity<GenericResponseBody<ProductResp>> updateProduct(
            @PathVariable Long productId,
            @RequestHeader(value = "loginId", required = false) String loginId,
            @RequestHeader(value = "estoken", required = false) String esToken,
            @Valid @RequestBody ProductUpdateReqDTO updateProductReq) throws JsonProcessingException {
        validationService.validate(updateProductReq);
        log.info("Request received for API={} with values : {} by user : {} ", "UPDATE_PRODUCT_API", updateProductReq, loginId);
        ProductResp resp = productDataService.updateProduct(productId, updateProductReq, loginId, esToken);
        GenericResponseBody<ProductResp> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
