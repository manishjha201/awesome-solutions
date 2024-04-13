package com.eshop.app.services;

import com.eshop.app.common.entities.rdbms.CartProduct;
import com.eshop.app.common.entities.rdbms.PaymentRequest;
import com.eshop.app.common.repositories.rdbms.master.CartItemRepository;
import com.eshop.app.common.repositories.rdbms.master.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    public List<CartProduct> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartProduct addCartItem(CartProduct cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    public PaymentRequest submitCart(Long userId, String paymentChannel) {
        List<CartProduct> cartItems = cartItemRepository.findByUserId(userId);
         // double totalAmount = cartItems.stream()
         //       .mapToDouble(item -> item.getQuantity() * getProductPrice(item.getProductId()))
         //       .sum();

        //PaymentRequest paymentRequest = new PaymentRequest(userId, totalAmount, "PENDING", paymentChannel, LocalDateTime.now());
       // return paymentRequestRepository.save(paymentRequest);
        return null;
    }
}
