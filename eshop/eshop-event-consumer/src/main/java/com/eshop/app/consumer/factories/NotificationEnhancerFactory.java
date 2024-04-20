package com.eshop.app.consumer.factories;

import com.eshop.app.common.constants.NotificationEventType;
import com.eshop.app.common.constants.ProductChangeEventNotificationType;
import com.eshop.app.consumer.strategy.INotificationDataGeneratorStrategy;
import com.eshop.app.consumer.strategy.LowInInventoryTemplateStrategy;
import org.hibernate.bytecode.enhance.spi.Enhancer;

import java.util.HashMap;
import java.util.Map;

public class NotificationEnhancerFactory {

    Map<String, INotificationDataGeneratorStrategy> dataGeneratorStrategyMap;

    public NotificationEnhancerFactory() {
        dataGeneratorStrategyMap = new HashMap<>();
        dataGeneratorStrategyMap.put(NotificationEventType.LOW_IN_INVENTORY.toString(), new LowInInventoryTemplateStrategy());
    }

    public INotificationDataGeneratorStrategy get(ProductChangeEventNotificationType type) {
        return dataGeneratorStrategyMap.getOrDefault(type.getLabel(), new LowInInventoryTemplateStrategy());
    }
}
