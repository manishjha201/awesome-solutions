package com.eshop.app.consumer.services;

import com.eshop.app.common.models.EShoppingChangeEvent;

public interface IEsDataIngestionService {
    boolean create(EShoppingChangeEvent changeEvent);
    boolean update(EShoppingChangeEvent changeEvent);
    boolean delete(EShoppingChangeEvent changeEvent);
}
