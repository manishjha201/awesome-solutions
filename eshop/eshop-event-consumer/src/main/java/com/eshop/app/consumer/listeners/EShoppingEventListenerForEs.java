package com.eshop.app.consumer.listeners;

import com.eshop.app.consumer.controller.ProductChangeEventController;
import com.eshop.app.consumer.exceptions.BusinessException;
import com.eshop.app.consumer.factories.EShoppingEventHandlerFactory;
import com.eshop.app.consumer.filters.IEsShoppingFeedFilter;
import com.eshop.app.consumer.models.ProcessedFeedOutput;
import com.eshop.app.consumer.parser.IEshopEventInfoParser;
import com.eshop.app.consumer.services.IEsDataIngestionService;
import com.eshop.app.consumer.services.INotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PropertySource("file:${config.file.path}config.properties")
public final class EShoppingEventListenerForEs {

    private final IEshopEventInfoParser eshopEventInfoParser;
    private final IEsShoppingFeedFilter esShoppingFeedFilter;
    private final IEsDataIngestionService esDataIngestionService;
    private final INotificationService notificationService;

    @Autowired
    public EShoppingEventListenerForEs(IEshopEventInfoParser eshopEventInfoParser, IEsShoppingFeedFilter esShoppingFeedFilter, IEsDataIngestionService esDataIngestionService, INotificationService notificationService) {
        this.eshopEventInfoParser = eshopEventInfoParser;
        this.esDataIngestionService = esDataIngestionService;
        this.esShoppingFeedFilter = esShoppingFeedFilter;
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${eshop.kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory", autoStartup = "true")
    public void kafkaEventListener(String msg) {
        long startTime = System.currentTimeMillis();
        try {
            log.debug("Input... [String]", msg);
            final ProcessedFeedOutput output = EShoppingEventHandlerFactory.get(
                    ProductChangeEventController.builder()
                            .message(msg)
                            .filter(this.esShoppingFeedFilter)
                            .notificationService(this.notificationService)
                            .esDataIngestionService(this.esDataIngestionService)
                            .parser(this.eshopEventInfoParser)
                    .build()
            ).process();
            if (!output.getIsSuccess()) {
                log.error("Processing of event failed : {}", output);
                //TODO : SEND ERROR MSG TO RETRY QUEUE
            } else {
                log.debug(" Event processed successFully... {}", output);
            }
        } catch(BusinessException e) {
            //TODO : SEND ERROR MSG TO DLQ
        } finally {
            long timeTaken = System.currentTimeMillis() - startTime;
            log.info("processing the Event took {} ms", timeTaken);
        }
    }


}
