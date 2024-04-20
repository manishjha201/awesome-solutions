package com.eshop.app.consumer.factories;

import com.eshop.app.consumer.controller.ProductChangeEventController;
import com.eshop.app.consumer.handlers.EShoppingEventHandler;
import com.eshop.app.consumer.handlers.IEShoppingEventHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EShoppingEventHandlerFactory {

    public static IEShoppingEventHandler get(ProductChangeEventController controllerInfo) {
        return EShoppingEventHandler.builder()
                .esShoppingFeedFilter(controllerInfo.getFilter())
                .eshopEventInfoParser(controllerInfo.getParser())
                .esDataIngestionService(controllerInfo.getEsDataIngestionService())
                .message(controllerInfo.getMessage())
                .build();
    }
}
