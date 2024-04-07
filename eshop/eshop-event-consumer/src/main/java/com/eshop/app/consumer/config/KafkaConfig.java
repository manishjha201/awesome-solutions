package com.eshop.app.consumer.config;

import com.eshop.app.consumer.constants.KafkaConstants;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@PropertySource("file:${config.file.path}config.properties")
public class KafkaConfig {

    @Value( value = "${eshop.kafka.bootstrap.server.address}")
    private String bootStrapAddress;

    @Value( value = "${eshop.kafka.group.id}")
    private String groupId;

    @Value( value = "${eshop.kafka.auto-offset-reset}")
    private String autoOffSetReset;

    @Value( value = "${eshop.kafka.consumer.concurrency}")
    private Integer consumerConcurrency;

    @Value( value = "${eshop.kafka.max.poll.records}")
    private Integer maxPollRecords;

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", bootStrapAddress);
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        props.put("group.id", groupId);
        props.put("max.poll.records", maxPollRecords);
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(Math.min(consumerConcurrency, KafkaConstants.MAX_ALLOWED_CONCURRENCY));
        return factory;
    }



}
