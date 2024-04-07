package com.eshop.app.common.entities.nosql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
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
    private UUID tenantId;
    private boolean isActive;
}
