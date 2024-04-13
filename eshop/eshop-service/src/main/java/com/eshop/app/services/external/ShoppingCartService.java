package com.eshop.app.services.external;

import com.eshop.app.common.entities.rdbms.*;
import com.eshop.app.common.repositories.nosql.es.ProductRepository;
import com.eshop.app.common.repositories.rdbms.master.CartRepository;
import com.eshop.app.common.repositories.rdbms.master.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    public void setPaymentDetails(Long cartId, PaymentRequest paymentDetails) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new Exception("Cart not found"));
        cart.setPaymentRequest(paymentDetails);
        cartRepository.save(cart);
    }

    public void processPayment(Long cartId) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new Exception("Cart not found"));
        PaymentRequest paymentDetails = cart.getPaymentRequest();
        if (paymentDetails == null) {
            throw new Exception("Payment details not set");
        }

        // Here, handle different payment methods accordingly
        if ("CreditCard".equals(paymentDetails.getPaymentMethod())) {
            // Process credit card payment
        } else if ("PayPal".equals(paymentDetails.getPaymentMethod())) {
            // Process PayPal payment
        }
        // More payment methods as needed

        // Process inventory and cart clearing as previously
        for (CartProduct item : cart.getItems()) {
            Product product = item.getProduct();
            int remainingStock = product.getInventory().getQuantity() - item.getQuantity();
            product.setInventory(Inventory.builder().build());
            //productRepository.save(product);
        }
        cartRepository.delete(cart);
    }
}
