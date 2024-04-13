package com.eshop.app.services;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.entities.rdbms.Cart;
import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.common.repositories.rdbms.master.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart getCarDetail(Long cartId) {
       return  cartRepository.findById(cartId).orElseThrow(() -> new BusinessException(EShopResultCode.NOT_FOUND.getResultCode()));
    }
}
