package com.eshop.app.services;

import com.eshop.app.models.resp.CartProductResp;

public interface ICartItemService {
    CartProductResp getCartItemsByUserId(Long userId, String loginId, String token);
}
