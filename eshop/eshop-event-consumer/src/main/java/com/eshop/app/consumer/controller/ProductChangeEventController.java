package com.eshop.app.consumer.controller;

import com.eshop.app.consumer.filters.IEsShoppingFeedFilter;
import com.eshop.app.consumer.parser.IEshopEventInfoParser;
import com.eshop.app.consumer.services.IEsDataIngestionService;
import com.eshop.app.consumer.services.INotificationService;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductChangeEventController {
    private Object message;
    private IEshopEventInfoParser parser;
    private IEsShoppingFeedFilter filter;
    private INotificationService notificationService;
    private IEsDataIngestionService esDataIngestionService;
}
