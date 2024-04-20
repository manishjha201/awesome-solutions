package com.eshop.app.consumer.strategy;

import com.eshop.app.common.models.EShoppingChangeEvent;

public interface IChangeEventDetectionStrategy {
    boolean process(EShoppingChangeEvent changeEvent);
}
