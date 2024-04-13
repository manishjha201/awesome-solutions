package com.eshop.app.consumer.strategy;

import com.eshop.app.consumer.controller.ProductChangeNotificationFlowController;

public class LowInInventoryTemplateStrategy implements INotificationDataGeneratorStrategy {

    @Override
    public ProductChangeNotificationFlowController enhance(ProductChangeNotificationFlowController controller) {
        return null;
    }
}
