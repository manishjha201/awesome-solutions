package com.eshop.app.config;

import com.eshop.app.kafka.ObjectSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@PropertySource("file:${config.file.path}config.properties")
public class KafkaConfig {
    @Value( value = "${eshop.kafka.bootstrap.server.address}")
    private String bootStrapAddress;

    @Value( value = "${eshop.kafka.producer.max.poll.records}")
    private Integer maxPollRecords;

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", bootStrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ObjectSerializer.class.getName());
        props.put("max.poll.records", maxPollRecords);
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> kafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(kafkaProducerFactory());
    }

}
