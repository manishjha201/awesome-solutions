package com.eshop.app.common.models.es;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@Data
public class Inventory {
    @Field(type = FieldType.Boolean)
    private Boolean inStock;

    @Field(type = FieldType.Integer)
    private Integer quantity;

    @Field(type = FieldType.Integer)
    private Integer minStockQuantity;

}
