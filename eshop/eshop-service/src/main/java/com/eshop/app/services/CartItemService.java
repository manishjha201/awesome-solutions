package com.eshop.app.services;

import com.eshop.app.client.ProductClient;
import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.entities.rdbms.Cart;
import com.eshop.app.common.entities.rdbms.CartProduct;
import com.eshop.app.common.entities.rdbms.PaymentRequest;
import com.eshop.app.common.entities.rdbms.User;
import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.common.models.kafka.Product;
import com.eshop.app.common.repositories.rdbms.master.CartItemRepository;
import com.eshop.app.common.repositories.rdbms.master.CartRepository;
import com.eshop.app.common.repositories.rdbms.master.PaymentRequestRepository;
import com.eshop.app.intercepters.UserContext;
import com.eshop.app.mapper.req.RequestMapper;
import com.eshop.app.models.req.CartProductReq;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.CartProductResp;
import com.eshop.app.models.resp.ProductResp;
import com.eshop.app.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartItemService implements ICartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public CartProductResp getCartItemsByUserId(Long userId, String loginId, String token) {
        User user = UserContext.getUserDetail();
        if (ObjectUtils.isEmpty(user)) throw new BusinessException(EShopResultCode.INVALID_INPUT.getResultCode());
        log.info("processing request for user : id : {}, login-id : {} ", userId, loginId);
        List<CartProduct> cartProducts = cartItemRepository.findByUserId(userId);
        CartProductResp resp = CartProductResp.builder().cartProducts(cartProducts).build();
        return resp;
    }

    @Transactional
    public CartProductResp addCartItem(CartProductReq dto, String loginId, String token) throws JsonProcessingException {
        User user = UserContext.getUserDetail();
        if (ObjectUtils.isEmpty(user)) throw new BusinessException(EShopResultCode.INVALID_INPUT.getResultCode());
        Product existing = productClient.getProduct(dto.getCartItem().getProduct().getId(), loginId, token).getProduct();
        if (existing.getInventory().getMinStockQuantity() > (existing.getInventory().getQuantity() - existing.getInventory().getReservedQuantity() - dto.getCartItem().getQuantity()) ) {
            throw new BusinessException(EShopResultCode.PRODUCT_STOCK_NOT_AVAILABLE.getResultCode());
        }
        log.info("processing request for input : {} for user : {} ", dto, loginId);
        CartProduct product = cartItemRepository.save(dto.getCartItem()); //Enable Caching if better performance needed
        existing.getInventory().setReservedQuantity(existing.getInventory().getReservedQuantity() + dto.getCartItem().getQuantity());
        productClient.updateProduct(existing.getId(), RequestMapper.preparerequest(existing, user),  loginId, token);
        return CartProductResp.builder().cartProducts(List.of(product)).build();
    }

    public void removeCartItem(Long id, String loginId, String token) {
        cartItemRepository.deleteById(id);
    }

    public PaymentRequest submitCart(Long userId, String paymentChannel, String loginId, String token) {
        List<CartProduct> cartItems = cartItemRepository.findByUserId(userId);
         // double totalAmount = cartItems.stream()
         //       .mapToDouble(item -> item.getQuantity() * getProductPrice(item.getProductId()))
         //       .sum();

        //PaymentRequest paymentRequest = new PaymentRequest(userId, totalAmount, "PENDING", paymentChannel, LocalDateTime.now());
       // return paymentRequestRepository.save(paymentRequest);
        return null;
    }

    public void addToCart(Long cartId, Long productId, int quantity, String loginId, String token) throws JsonProcessingException {
        User user = UserContext.getUserDetail();
        Optional<Cart> existingCartOptional =  cartRepository.findById(cartId);
        Cart existingCart = existingCartOptional.orElseThrow(() -> new BusinessException(EShopResultCode.NOT_FOUND.getResultCode()));
        ProductResp product = productClient.getProduct(productId, loginId, token);
        existingCart.getItems().add(RequestMapper.buildCartProduct(existingCart , product, cartId, quantity));
        Product existing = productClient.getProduct(productId, loginId, token).getProduct();
        if (existing.getInventory().getMinStockQuantity() > (existing.getInventory().getQuantity() - existing.getInventory().getReservedQuantity() - quantity) ) {
            throw new BusinessException(EShopResultCode.PRODUCT_STOCK_NOT_AVAILABLE.getResultCode());
        }
        cartRepository.save(existingCart);
        existing.getInventory().setReservedQuantity(existing.getInventory().getReservedQuantity() + quantity);
        productClient.updateProduct(existing.getId(), RequestMapper.preparerequest(existing, user),  loginId, token);
    }
}
