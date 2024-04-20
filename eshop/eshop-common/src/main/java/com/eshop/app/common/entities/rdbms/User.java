package com.eshop.app.common.entities.rdbms;

import javax.persistence.*;
import com.eshop.app.common.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_active")
    private boolean isActive;

    private int version;

    public com.eshop.app.common.models.kafka.User build() {
        com.eshop.app.common.models.kafka.User user = new com.eshop.app.common.models.kafka.User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setLoginId(this.loginId);
        user.setRole(this.role.name());
        user.setTenantId(this.tenantId);
        user.setActive(this.isActive);
        user.setVersion(this.version);
        return user;
    }
}
