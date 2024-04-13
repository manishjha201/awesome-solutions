package com.eshop.app.common.models.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory implements Serializable {
    private static final long serialVersionUID = -8309789912564690435L;

    private Long id;
    private Boolean inStock;
    private Integer quantity;
    private Integer reservedQuantity;
    private Integer minStockQuantity;
    private Long productId;
}
