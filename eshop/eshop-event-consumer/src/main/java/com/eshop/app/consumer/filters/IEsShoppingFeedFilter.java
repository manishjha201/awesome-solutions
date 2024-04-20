package com.eshop.app.consumer.filters;

import com.eshop.app.common.models.EShoppingChangeEvent;

public interface IEsShoppingFeedFilter {
    EShoppingChangeEvent filter(EShoppingChangeEvent  eShoppingChangeEvent);
}
