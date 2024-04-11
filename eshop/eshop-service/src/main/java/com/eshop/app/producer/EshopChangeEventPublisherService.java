package com.eshop.app.producer;

import com.eshop.app.common.constants.AppConstants;
import com.eshop.app.common.models.kafka.EShoppingChangeEventKafka;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@PropertySource("file:${config.file.path}config.properties")
@Service
public class EshopChangeEventPublisherService implements IEshopChangeEventPublisherService {

    @Value( value = "${eshop.kafka.topic.name}")
    private String topicName;

    @Value( value = "${runtime.context.environment:dev}")
    private String env;

    @Value( value = "${runtime.context.appName:e-shopping-service}")
    private String appName;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String key, EShoppingChangeEventKafka event) {
        Message<EShoppingChangeEventKafka> message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC , topicName)
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .copyHeaders(getKafkaHeaders())
                .build();

        ListenableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(message);
        future.addCallback(
                new ListenableFutureCallback<>() {

                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        log.info(
                                "Sent message with key = {} and offset = {}",
                                key,
                                result.getRecordMetadata().offset());
                        log.debug("Sent message with key= {} and body = {}", key, event);
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.error(
                                "Unable to send message with key = {} with exception message = {} ",
                                key,
                                ex.getMessage());
                    }
                });
    }

    private Map<String, Object> getKafkaHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("CORRELATION_ID", MDC.get(AppConstants.REQUEST_ID));
        headers.put("environment", env);
        headers.put("appName", appName);
        return headers;
    }
}
