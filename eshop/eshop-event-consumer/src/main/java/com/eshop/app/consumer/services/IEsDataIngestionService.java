package com.eshop.app.consumer.services;

import com.eshop.app.common.models.EShoppingChangeEvent;

public interface IEsDataIngestionService {
    boolean updateEsDataSource(EShoppingChangeEvent changeEvent);
}
