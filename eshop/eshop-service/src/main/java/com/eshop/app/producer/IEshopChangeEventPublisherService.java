package com.eshop.app.producer;

import com.eshop.app.common.models.kafka.EShoppingChangeEventKafka;

public interface IEshopChangeEventPublisherService {
    void publish(String key, EShoppingChangeEventKafka event);
}
