package com.eshop.app.consumer.parser;

import com.eshop.app.common.models.EShoppingChangeEvent;

public interface IEshopEventInfoParser {
    EShoppingChangeEvent parse(Object json);
}
