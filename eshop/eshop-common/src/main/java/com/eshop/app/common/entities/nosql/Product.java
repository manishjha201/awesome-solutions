package com.eshop.app.common.entities.nosql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table("product")
public class Product {

    @PrimaryKey
    private UUID productId;

    private String name;

    private BigDecimal price;

    private UUID catalogId;

    private UUID categoryId;

    private UUID tenantId;

    private String imageUrl;

    private UUID createdBy;

    private LocalDateTime createdAt;

    private UUID lastUpdatedBy;

    private LocalDateTime lastUpdatedAt;

    private boolean isActive;

    private int version;

    public JsonNode toJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("productId", productId.toString());
        // Add other fields as needed
        return jsonNode;
    }

}

