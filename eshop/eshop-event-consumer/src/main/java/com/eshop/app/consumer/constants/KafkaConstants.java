package com.eshop.app.consumer.constants;

public enum KafkaConstants {
    A;

    public static final Integer MAX_ALLOWED_CONCURRENCY = 500; //TODO : TO BE DERIVED FROM LATENCY FIGURE
}
