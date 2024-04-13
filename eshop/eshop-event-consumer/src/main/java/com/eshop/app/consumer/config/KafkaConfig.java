package com.eshop.app.consumer.config;

import com.eshop.app.common.models.kafka.ObjectSerializer;
import com.eshop.app.consumer.constants.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

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
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ObjectSerializer.class.getName());
        props.put("max.poll.records", maxPollRecords);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
