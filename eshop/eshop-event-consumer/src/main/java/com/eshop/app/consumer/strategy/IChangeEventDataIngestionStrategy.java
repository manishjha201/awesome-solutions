package com.eshop.app.consumer.strategy;

import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.consumer.services.IEsDataIngestionService;

public interface IChangeEventDataIngestionStrategy {
    boolean process(IEsDataIngestionService service, EShoppingChangeEvent changeEvent);
}
