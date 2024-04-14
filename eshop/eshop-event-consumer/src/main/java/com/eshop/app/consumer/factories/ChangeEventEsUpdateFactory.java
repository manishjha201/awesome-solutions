package com.eshop.app.consumer.factories;

import com.eshop.app.common.constants.ChangeType;
import com.eshop.app.consumer.strategy.*;

import java.util.HashMap;
import java.util.Map;

public class ChangeEventEsUpdateFactory {
    private final Map<String , IChangeEventDataIngestionStrategy> map;
    private final  IChangeEventDataIngestionStrategy defaultStrategy;
    public ChangeEventEsUpdateFactory() {
        map = new HashMap<>();
        defaultStrategy =  new ChangeEventDataIngestionStrategyForDefault();
        map.put(ChangeType.CREATE.toString(), new ChangeEventDataIngestionStrategyForCreate());
        map.put(ChangeType.UPDATE.toString(), new ChangeEventDataIngestionStrategyForUpdate()); //
        map.put(ChangeType.UPDATE.toString(), new ChangeEventDataIngestionStrategyForDelete());
    }
    public IChangeEventDataIngestionStrategy get(String changeType) {
        return map.getOrDefault(changeType, defaultStrategy);
    }
}
