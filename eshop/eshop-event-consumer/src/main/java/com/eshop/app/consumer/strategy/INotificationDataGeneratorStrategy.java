package com.eshop.app.consumer.strategy;

import com.eshop.app.consumer.controller.ProductChangeNotificationFlowController;

public interface INotificationDataGeneratorStrategy {
    ProductChangeNotificationFlowController enhance(ProductChangeNotificationFlowController controller);
}
