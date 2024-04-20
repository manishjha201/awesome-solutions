package com.eshop.app.common.models.kafka;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonDeserialize(builder = EShoppingChangeEventKafka.EShoppingChangeEventKafkaBuilder.class)
public class EShoppingChangeEventKafka implements Serializable {
    private static final long serialVersionUID = -2325329761371087751L;
    private String dataschema;
    private String data;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EShoppingChangeEventKafkaBuilder {}
}
