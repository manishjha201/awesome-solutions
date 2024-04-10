package com.eshop.app.common.entities.nosql.cassandra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID catalogId;

    private String name;

    private UUID tenantId;

    private UUID createdBy;

    private LocalDateTime createdAt;

    private UUID lastUpdatedBy;

    private LocalDateTime lastUpdatedAt;

    private boolean isActive;

    private int version;

    @ElementCollection
    @MapKeyColumn(name = "locale")
    @Column(name = "value")
    @CollectionTable(name = "catalog_i18n", joinColumns = @JoinColumn(name = "catalog_id"))
    private Map<String, String> nameI18n;

}

