package com.eshop.app.consumer.parser;

import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.common.models.kafka.EShoppingChangeEventKafka;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EshopEventInfoParser implements  IEshopEventInfoParser {
    private static final Gson gson = new Gson();
    @Override
    public EShoppingChangeEvent parse(Object json) {
        try {
            EShoppingChangeEventKafka kafkaEvent = (EShoppingChangeEventKafka) json;
            log.debug("Parsing kafka payload {} ", json);
            log.info("Parsing event info {}", kafkaEvent);
            return ObjectUtils.isNotEmpty(kafkaEvent) ? gson.fromJson(kafkaEvent.getData(), EShoppingChangeEvent.class) : null;
        } catch(Exception e) {
            log.error(" Error occured while parsing Kafka Change Event {} ", json, e);
            return null;
        }
    }
}
