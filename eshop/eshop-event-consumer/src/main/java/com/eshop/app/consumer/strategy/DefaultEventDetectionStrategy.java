package com.eshop.app.consumer.strategy;

import com.eshop.app.common.models.EShoppingChangeEvent;

public class DefaultEventDetectionStrategy implements IChangeEventDetectionStrategy {
    @Override
    public boolean process(EShoppingChangeEvent changeEvent) {
        return false;
    }
}
