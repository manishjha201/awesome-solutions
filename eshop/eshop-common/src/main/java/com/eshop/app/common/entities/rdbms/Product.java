package com.eshop.app.common.entities.rdbms;

import com.eshop.app.common.constants.Currency;
import com.eshop.app.common.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refID;

    private String title;

    private String name;

    private String description;

    private BigDecimal price;

    private Currency currency;

    @OneToOne(targetEntity = Inventory.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Inventory inventory;

    /*
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Category category;
    */

    private int count;

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
    private String createdBy;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    private int version;
}
