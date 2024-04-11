package com.eshop.app.common.models.kafka;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = Inventory.InventoryBuilder.class)
@JsonSerialize
@ToString
public class Inventory implements Serializable {

    private Long id;
    private Boolean inStock;
    private Integer quantity;
    private Integer reservedQuantity;
    private Integer minStockQuantity;
    private Long productId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class InventoryBuilder {}
}
