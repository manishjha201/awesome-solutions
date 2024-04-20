package com.eshop.app.common.entities.rdbms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "in_stock")
    private Boolean inStock;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity;

    @Column(name = "min_stock_quantity")
    private Integer minStockQuantity;

    @Column(name = "product_id")
    private Long productId;

    public com.eshop.app.common.models.kafka.Inventory build() {
        com.eshop.app.common.models.kafka.Inventory inventory = new com.eshop.app.common.models.kafka.Inventory();
        inventory.setId(this.id);
        inventory.setInStock(this.inStock);
        inventory.setQuantity(this.quantity);
        inventory.setReservedQuantity(this.reservedQuantity);
        inventory.setMinStockQuantity(this.minStockQuantity);
        inventory.setProductId(this.productId);
        return inventory;
    }
}

