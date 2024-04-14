package com.eshop.app.consumer.strategy;

import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.consumer.services.IEsDataIngestionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChangeEventDataIngestionStrategyForCreate implements IChangeEventDataIngestionStrategy {

    @Override
    public boolean process(IEsDataIngestionService service, EShoppingChangeEvent changeEvent) {
        try {
            return service.create(changeEvent);
        } catch(Exception e) {
            log.error("Exception occurred : ", e);
        }
        return false;
    }
}
