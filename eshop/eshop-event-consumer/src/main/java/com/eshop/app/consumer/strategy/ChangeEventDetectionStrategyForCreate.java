package com.eshop.app.consumer.strategy;

import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.common.models.kafka.Product;

public class ChangeEventDetectionStrategyForCreate implements IChangeEventDetectionStrategy {
    @Override
    public boolean process(EShoppingChangeEvent changeEvent) {
        Product newProduct = changeEvent.getProductChangeEvent().getCurrentValue();
        return newProduct.getInventory().getInStock() && newProduct.getInventory().getQuantity().compareTo(newProduct.getInventory().getMinStockQuantity()) <= 0 ;
    }
}
