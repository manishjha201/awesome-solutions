package com.eshop.app.common.models;

import com.eshop.app.common.entities.nosql.cassandra.Product;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ProductChangeEvent implements Serializable {
    private Product previousValue;
    private Product currentValue;
}
