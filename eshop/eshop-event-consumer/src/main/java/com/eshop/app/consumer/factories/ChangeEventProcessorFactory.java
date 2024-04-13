package com.eshop.app.consumer.factories;

import com.eshop.app.common.constants.ChangeType;
import com.eshop.app.consumer.strategy.ChangeEventDetectionStrategyForCreate;
import com.eshop.app.consumer.strategy.DefaultEventDetectionStrategyForCreate;
import com.eshop.app.consumer.strategy.IChangeEventDetectionStrategy;
import java.util.HashMap;
import java.util.Map;

public final class ChangeEventProcessorFactory {
    private final Map<String, IChangeEventDetectionStrategy> dataGeneratorStrategyMap;
    private final IChangeEventDetectionStrategy defaultStrategy;

    public ChangeEventProcessorFactory() {
        dataGeneratorStrategyMap = new HashMap<>();
        defaultStrategy = new DefaultEventDetectionStrategyForCreate();
        dataGeneratorStrategyMap.put(ChangeType.CREATE.toString(), new ChangeEventDetectionStrategyForCreate());
        dataGeneratorStrategyMap.put(ChangeType.UPDATE.toString(), new ChangeEventDetectionStrategyForCreate());
    }

    public IChangeEventDetectionStrategy get(String changeType) {
        return dataGeneratorStrategyMap.getOrDefault(changeType, defaultStrategy);
    }
}
