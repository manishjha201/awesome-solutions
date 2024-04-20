package com.eshop.app.services;

import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.models.req.CartPaymentReq;

public interface IEShoppingCartService {
    void setPaymentDetails(CartPaymentReq dto, Long cartId, String loginId, String token) throws BusinessException;
    void updatePaymentDetails(CartPaymentReq dto, Long paymentRequestId, Long cartId, String loginId, String token) throws BusinessException;
    void processPayment(CartPaymentReq dto, Long paymentRequestId, Long cartId, String loginId, String token) throws BusinessException;
}
