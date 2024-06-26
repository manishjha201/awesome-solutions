package com.eshop.app.common.entities.rdbms;

import com.eshop.app.common.constants.Status;
import com.eshop.app.common.constants.TenantType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TenantType type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    private int version;

    public com.eshop.app.common.models.kafka.Tenant build() {
        com.eshop.app.common.models.kafka.Tenant tenant = new com.eshop.app.common.models.kafka.Tenant();
        tenant.setId(this.id);
        tenant.setName(this.name);
        tenant.setType(this.type.getLabel());
        tenant.setCreatedBy(this.createdBy);
        tenant.setLastUpdatedBy(this.lastUpdatedBy);
        tenant.setStatus(this.status);
        tenant.setVersion(this.version);
        return tenant;
    }
}
