package com.eshop.app.common.entities.nosql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("inventory")
public class Inventory {
    @PrimaryKey
    private UUID productId;

    private int count;
    private UUID tenantId;
    private UUID version;
    private UUID lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;
}
