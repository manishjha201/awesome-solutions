package com.eshop.app.mapper.req;

import com.eshop.app.common.entities.rdbms.Inventory;
import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.common.entities.rdbms.User;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.req.ProductUpdateReqDTO;

public class RequestMapper {

    public static Product buildProductFromProductReqDTO(ProductReqDTO dto, User user) {
        return Product.builder().title(dto.getTitle()).name(dto.getName()).description(dto.getDescription()).price(dto.getPrice())
                .currency(dto.getCurrency())
                .inventory(Inventory.builder().inStock(dto.getInStock()).quantity(dto.getQuantity()).reservedQuantity(0).minStockQuantity(dto.getMinStockQuantity()).build())
                .status(dto.getStatus()).categoryId(dto.getCategoryId()).imageUrl(dto.getImageUrl()).tenantId(dto.getTenantId())
                .createdBy(user.getId())
                .lastUpdatedBy(user.getId())
                .isUpdatedToES(Boolean.FALSE)
                .version(1)
                .build();
    }

    public static Product updateProductFromProductReqDTO(ProductUpdateReqDTO dto, User user) {
        return Product.builder().title(dto.getTitle()).name(dto.getName()).description(dto.getDescription()).price(dto.getPrice())
                .currency(dto.getCurrency())
                .inventory(Inventory.builder().inStock(dto.getInStock()).quantity(dto.getQuantity()).reservedQuantity(0).minStockQuantity(dto.getMinStockQuantity()).build())
                .status(dto.getStatus()).categoryId(dto.getCategoryId()).imageUrl(dto.getImageUrl()).tenantId(dto.getTenantId())
                .createdBy(user.getId())
                .lastUpdatedBy(user.getId())
                .isUpdatedToES(Boolean.FALSE)
                .version(dto.getVersionNo())
                .build();
    }
}
