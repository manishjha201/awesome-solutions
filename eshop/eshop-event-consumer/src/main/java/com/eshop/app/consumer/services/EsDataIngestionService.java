package com.eshop.app.consumer.services;

import com.eshop.app.common.models.EShoppingChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EsDataIngestionService implements IEsDataIngestionService {

    @Override
    public boolean updateEsDataSource(EShoppingChangeEvent changeEvent) {
        //TODO : pending
        return true;
    }

}
