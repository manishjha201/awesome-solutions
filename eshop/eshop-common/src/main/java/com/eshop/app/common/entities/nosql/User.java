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
@Table("user")
public class User {
    @PrimaryKey
    private UUID userId;

    private String username;
    private String password;
    private String role; //TODO : Consider using an enum for limited values (ADMIN, USER)
    private UUID tenantId;
    private boolean isActive;
}
