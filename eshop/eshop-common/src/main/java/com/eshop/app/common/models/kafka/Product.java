package com.eshop.app.common.models.kafka;

import com.eshop.app.common.constants.Currency;
import com.eshop.app.common.constants.Status;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = Product.ProductBuilder.class)
@JsonSerialize
@ToString
public class Product implements Serializable {
    private static final long serialVersionUID = -281542741856338210L;
    private Long id;
    private String refID;
    private String title;
    private String name;
    private String description;
    private Double price;
    private String currency;
    private Inventory inventory;
    private Status status;
    private Long categoryId;
    private String imageUrl;
    private Long tenantId;
    private Boolean isUpdatedToES;
    private int version;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductBuilder {}
}
