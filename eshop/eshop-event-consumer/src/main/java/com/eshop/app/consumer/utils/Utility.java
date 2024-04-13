package com.eshop.app.consumer.utils;

import com.eshop.app.common.constants.Currency;
import com.eshop.app.common.entities.nosql.es.Category;
import com.eshop.app.common.entities.nosql.es.Inventory;
import com.eshop.app.common.entities.nosql.es.Product;
import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.consumer.models.ProductUpdateReqDTO;

import java.math.BigDecimal;

public class Utility {

    public static Product getProduct(EShoppingChangeEvent changeEvent, com.eshop.app.common.entities.rdbms.Category category) {
        com.eshop.app.common.models.kafka.Product current = changeEvent.getProductChangeEvent().getCurrentValue();
        return Product.builder().productID(current.getId().toString()).title(current.getTitle()).name(current.getName()).description(current.getDescription())
                .price(current.getPrice()).currency(current.getCurrency()).status(current.getStatus())
                .category(Category.builder().categoryID(category.getId().toString()).name(category.getName()).description(category.getDescription()).build())  //to get from application cache
                .inventory(Inventory.builder()
                        .inStock(current.getInventory().getInStock()) //to get from app cache TENTATIVE
                        .minStockQuantity(current.getInventory().getMinStockQuantity()) ////to get from app cache TENTATIVE
                        .quantity(current.getInventory().getQuantity()).build()) ////to get from app cache TENTATIVE
                .build();
    }

    public static ProductUpdateReqDTO buildProductUpdateReqDTO(String refId, EShoppingChangeEvent changeEvent) {
        com.eshop.app.common.models.kafka.Product productValue = changeEvent.getProductChangeEvent().getCurrentValue();
        return ProductUpdateReqDTO.builder().refId(refId)
                .title(productValue.getTitle()).name(productValue.getName()).description(productValue.getDescription())
                .price(new BigDecimal(productValue.getPrice()))
                .currency(Currency.valueOf(productValue.getCurrency()))
                .status(productValue.getStatus())
                .categoryId(productValue.getCategoryId())
                .inStock(productValue.getInventory().getInStock())
                .minStockQuantity(productValue.getInventory().getMinStockQuantity())
                .quantity(productValue.getInventory().getQuantity())
                .build();
    }
}
