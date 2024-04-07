package com.eshop.app.consumer.handlers;

import com.eshop.app.consumer.exceptions.BusinessException;
import com.eshop.app.consumer.models.ProcessedFeedOutput;

public interface IEShoppingEventHandler {
    ProcessedFeedOutput process() throws BusinessException;
}
