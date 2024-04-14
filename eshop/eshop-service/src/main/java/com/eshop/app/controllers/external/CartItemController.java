package com.eshop.app.controllers.external;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.models.req.CartPaymentReq;
import com.eshop.app.models.req.CartProductReq;
import com.eshop.app.models.resp.CartProductResp;
import com.eshop.app.models.resp.GenericResponseBody;
import com.eshop.app.models.resp.ResultInfo;
import com.eshop.app.services.CartItemService;
import com.eshop.app.services.IValidationService;
import com.eshop.app.services.external.ShoppingCartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/user/api/v1/cart")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private IValidationService validationService;

    //TODO : Add swagger
    @GetMapping("/{userId}")
    public ResponseEntity<GenericResponseBody<CartProductResp>> getCartItems(@Valid @PathVariable Long userId, @RequestHeader(value = "loginId", required = false) String loginId, @RequestHeader(value = "estoken", required = true) String estoken) {
        CartProductResp resp = cartItemService.getCartItemsByUserId(userId, loginId, estoken);
        GenericResponseBody<CartProductResp> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    //TODO : Add swagger
    @PostMapping
    public ResponseEntity<GenericResponseBody<CartProductResp>> addCartItem(@RequestBody CartProductReq dto, @RequestHeader(value = "loginId", required = false) String loginId, @RequestHeader(value = "estoken", required = true) String estoken) throws JsonProcessingException {
        validationService.validate(dto);
        CartProductResp resp = cartItemService.addCartItem(dto, loginId, estoken);
        GenericResponseBody<CartProductResp> body = new GenericResponseBody<>();
        body.setResponse(resp);
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
    }

    /**
     * To be used only in case of physical delete of records.
     * @param cartId
     * @param loginId
     * @param estoken
     * @return
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartId, @RequestHeader(value = "loginId", required = false) String loginId, @RequestHeader(value = "estoken", required = true) String estoken) {
        cartItemService.removeCartItem(cartId, loginId, estoken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/{cartId}") //TODO : PENDING : remove
    public ResponseEntity<?> addToCart(@PathVariable(required = true) Long cartId, @RequestParam Long productId, @RequestParam int quantity, @RequestHeader(value = "loginId", required = false) String loginId, @RequestHeader(value = "estoken", required = true) String estoken) {
        try {
            cartItemService.addToCart(cartId, productId, quantity, loginId, estoken);
            return ResponseEntity.ok("Item added to cart. Cart ID: " + cartId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{cartId}/payment/prepare")
    public ResponseEntity<?> createPaymentRequest(
            @RequestBody CartPaymentReq dto,
            @RequestParam(value = "cartId", required = true) Long cartId,
            @RequestHeader(value = "loginId", required = true) String loginId,
            @RequestHeader(value = "estoken", required = true) String estoken

    ) {
        try {
            validationService.validate(dto);
            shoppingCartService.setPaymentDetails(dto, cartId, loginId, estoken);
            return ResponseEntity.ok("Payment processed and inventory updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cartId}/payment/prepare/{paymentRequestId}")
    public ResponseEntity<?> processPayment(@RequestBody CartPaymentReq dto,  @PathVariable(required = true) Long cartId, @PathVariable(required = true) Long paymentRequestId, @RequestHeader(value = "loginId", required = false) String loginId, @RequestHeader(value = "estoken", required = true) String estoken) {
        try {
            validationService.validate(dto);
            shoppingCartService.updatePaymentDetails(dto, paymentRequestId, cartId, loginId, estoken);
            return ResponseEntity.ok("Payment processed and inventory updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{cartId}/payment/{paymentRequestId}/pay")
    public ResponseEntity<?> payPayment(@RequestBody CartPaymentReq dto,  @PathVariable(required = true) Long cartId, @PathVariable(required = true) Long paymentRequestId, @RequestHeader(value = "loginId", required = false) String loginId, @RequestHeader(value = "estoken", required = true) String estoken) {
        try {
            validationService.validate(dto);
            shoppingCartService.processPayment(dto, paymentRequestId, cartId, loginId, estoken);
            return ResponseEntity.ok("Payment processed and inventory updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
