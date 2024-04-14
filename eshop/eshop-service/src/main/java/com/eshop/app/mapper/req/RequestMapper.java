package com.eshop.app.mapper.req;

import com.eshop.app.common.constants.Currency;
import com.eshop.app.common.constants.PaymentStatus;
import com.eshop.app.common.entities.rdbms.*;
import com.eshop.app.models.req.CartPaymentReq;
import com.eshop.app.models.req.ProductReqDTO;
import com.eshop.app.models.req.ProductRequest;
import com.eshop.app.models.req.ProductUpdateReqDTO;
import com.eshop.app.models.resp.ProductResp;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public static Product updateProductFromProductReqDTO(Long productId, ProductUpdateReqDTO dto, User user) {
        return Product.builder().id(productId)
                .title(dto.getTitle()).name(dto.getName()).description(dto.getDescription()).price(dto.getPrice())
                .currency(dto.getCurrency())
                .inventory(Inventory.builder().inStock(dto.getInStock()).quantity(dto.getQuantity()).reservedQuantity(0).minStockQuantity(dto.getMinStockQuantity()).build())
                .status(dto.getStatus()).categoryId(dto.getCategoryId()).imageUrl(dto.getImageUrl()).tenantId(dto.getTenantId())
                .createdBy(user.getId())
                .lastUpdatedBy(user.getId())
                .isUpdatedToES(Boolean.FALSE)
                .version(dto.getVersionNo())
                .build();
    }

    public static ProductUpdateReqDTO preparerequest(com.eshop.app.common.models.kafka.Product dto, User user) {
        return ProductUpdateReqDTO.builder()
                .title(dto.getTitle()).name(dto.getName()).description(dto.getDescription()).price(new BigDecimal(dto.getPrice()).setScale(2, RoundingMode.HALF_DOWN) )
                .currency(Currency.valueOf(dto.getCurrency()))
                .inStock(dto.getInventory().getInStock())
                .minStockQuantity(dto.getInventory().getMinStockQuantity())
                .reservedQuantity(dto.getInventory().getReservedQuantity())
                .quantity(dto.getInventory().getQuantity())
                .status(dto.getStatus()).categoryId(dto.getCategoryId()).imageUrl(dto.getImageUrl()).tenantId(dto.getTenantId())
                .isUpdatedToES(Boolean.FALSE)
                .versionNo(dto.getVersion())
                .build();
    }

    public static CartProduct buildCartProduct(Cart existingCart, ProductResp productResp, Long cartId, int quantity) {
        com.eshop.app.common.models.kafka.Product dto = productResp.getProduct();
        com.eshop.app.common.entities.rdbms.Product productForDb = Product.
                builder()
                .title(dto.getTitle()).name(dto.getName()).description(dto.getDescription()).price(new BigDecimal(dto.getPrice()).setScale(2, RoundingMode.HALF_DOWN) )
                .currency(Currency.valueOf(dto.getCurrency()))
                .inventory(Inventory.builder().minStockQuantity(dto.getInventory().getMinStockQuantity()).reservedQuantity(dto.getInventory().getReservedQuantity())
                        .inStock(dto.getInventory().getInStock()).quantity(dto.getInventory().getQuantity()).build())
                .status(dto.getStatus()).categoryId(dto.getCategoryId()).imageUrl(dto.getImageUrl()).tenantId(dto.getTenantId())
                .isUpdatedToES(Boolean.FALSE)
                .version(dto.getVersion())
                .build();
        return CartProduct.builder().cart(existingCart).product(productForDb).quantity(quantity).build();
    }

    public static PaymentRequest buildPaymentRequest(CartPaymentReq dto, User user, BigDecimal amount) {
        int versionNo = dto.getVersion() == null ? 1 : dto.getVersion();
        return PaymentRequest.builder().userId(user.getId()).amount(amount.doubleValue())
                .paymentMethod(dto.getPaymentMethod())
                .status(PaymentStatus.INITIATED)
                .paymentChannel("gatewayName")
                .isActive(Boolean.TRUE)
                .tenantId(user.getTenantId())
                .version(versionNo)
                .build();
    }
}
