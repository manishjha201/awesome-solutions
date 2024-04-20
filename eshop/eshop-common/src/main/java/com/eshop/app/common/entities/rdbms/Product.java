package com.eshop.app.common.entities.rdbms;

import com.eshop.app.common.constants.Currency;
import com.eshop.app.common.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = -4248856148610251644L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refID;

    private String title;

    private String name;

    private String description;

    private BigDecimal price;

    private Currency currency;

    @OneToOne(targetEntity = Inventory.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Inventory inventory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    @Column(name = "is_es_updated")
    private Boolean isUpdatedToES;

    private int version;

    public com.eshop.app.common.models.kafka.Product build() {
        com.eshop.app.common.models.kafka.Product product = new com.eshop.app.common.models.kafka.Product();
        product.setId(this.id);
        product.setRefID(this.refID);
        product.setTitle(this.title);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price.doubleValue());
        product.setCurrency(this.currency.getCode());
        product.setInventory(this.inventory.build());
        product.setStatus(this.status);
        product.setCategoryId(this.categoryId);
        product.setImageUrl(this.imageUrl);
        product.setTenantId(this.tenantId);
        product.setIsUpdatedToES(this.isUpdatedToES);
        product.setVersion(this.version);
        return product;
    }
}
