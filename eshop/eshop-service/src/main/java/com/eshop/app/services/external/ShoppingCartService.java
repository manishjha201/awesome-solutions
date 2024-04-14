package com.eshop.app.services.external;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.entities.rdbms.*;
import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.common.repositories.nosql.es.ProductRepository;
import com.eshop.app.common.repositories.rdbms.master.CartRepository;
import com.eshop.app.common.repositories.rdbms.master.PaymentRequestRepository;
import com.eshop.app.intercepters.UserContext;
import com.eshop.app.mapper.req.RequestMapper;
import com.eshop.app.models.req.CartPaymentReq;
import com.eshop.app.services.IEShoppingCartService;
import com.eshop.app.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class ShoppingCartService implements IEShoppingCartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Override
    public void setPaymentDetails(CartPaymentReq dto, Long cartId, String loginId, String token) throws BusinessException {
        User user = UserContext.getUserDetail();
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new BusinessException(EShopResultCode.NOT_FOUND.getResultCode()));
        BigDecimal paymentAmount = Utility.preparePaymentAmount(cart);
        PaymentRequest paymentRequest = RequestMapper.buildPaymentRequest(dto, user, paymentAmount);
        cart.setPaymentRequest(paymentRequest);
        cartRepository.save(cart);
    }

    @Override
    public void updatePaymentDetails(CartPaymentReq dto, Long paymentRequestId, Long cartId, String loginId, String token) throws BusinessException {
        User user = UserContext.getUserDetail();
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new BusinessException(EShopResultCode.NOT_FOUND.getResultCode()));
        BigDecimal paymentAmount = Utility.preparePaymentAmount(cart);
        PaymentRequest paymentRequest = RequestMapper.buildPaymentRequest(dto, user, paymentAmount);
        paymentRequest.setId(paymentRequestId);
        cart.setPaymentRequest(paymentRequest);
        cartRepository.save(cart);
    }

    @Override
    public void processPayment(CartPaymentReq dto, Long paymentRequestId, Long cartId, String loginId, String token) throws BusinessException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new BusinessException(EShopResultCode.NOT_FOUND.getResultCode()));
        PaymentRequest paymentDetails = paymentRequestRepository.findById(paymentRequestId).orElseThrow(() -> new BusinessException(EShopResultCode.NOT_FOUND.getResultCode()));
        //TODO : Prepare purchase/ order fulfillment event and send over kafka topic.
        cartRepository.delete(cart);
    }
}
