package com.eshop.app.consumer.factories;

import com.eshop.app.common.constants.ChangeType;
import com.eshop.app.consumer.handlers.IEShoppingEventHandler;
import com.eshop.app.consumer.strategy.ChangeEventDetectionStrategyForCreate;
import com.eshop.app.consumer.strategy.DefaultEventDetectionStrategy;
import com.eshop.app.consumer.strategy.IChangeEventDetectionStrategy;
import java.util.HashMap;
import java.util.Map;

public class ChangeEventProcessorFactory {

    private final Map<String , IChangeEventDetectionStrategy> map;
    private final IChangeEventDetectionStrategy defaultStrategy;

    public ChangeEventProcessorFactory() {
        map = new HashMap<>();
        defaultStrategy = new DefaultEventDetectionStrategy();
        map.put(ChangeType.CREATE.toString(), new ChangeEventDetectionStrategyForCreate());
        map.put(ChangeType.UPDATE.toString(), new ChangeEventDetectionStrategyForCreate());
    }
    public IChangeEventDetectionStrategy get(String changeType) {
        return map.getOrDefault(changeType, defaultStrategy);
    }
}
