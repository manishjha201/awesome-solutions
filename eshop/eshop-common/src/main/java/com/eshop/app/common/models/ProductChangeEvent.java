package com.eshop.app.common.models;
import com.eshop.app.common.models.kafka.Inventory;
import com.eshop.app.common.models.kafka.Product;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = ProductChangeEvent.ProductChangeEventBuilder.class)
@JsonSerialize
@ToString
public class ProductChangeEvent implements Serializable {
    private Product previousValue;
    private Product currentValue;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductChangeEventBuilder {}
}
