package com.eshop.app.common.entities.nosql.cassandra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.Entity;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("category")
public class Category {
    @PrimaryKey
    private UUID categoryId;
    private String name;
    private String description; //TODO
    private UUID tenantId;
    private boolean isActive;
}
