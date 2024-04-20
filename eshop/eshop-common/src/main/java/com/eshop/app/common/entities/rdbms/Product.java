package com.eshop.app.common.entities.rdbms;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
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

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;
}
