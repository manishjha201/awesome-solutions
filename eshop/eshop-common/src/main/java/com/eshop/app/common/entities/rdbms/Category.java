package com.eshop.app.common.entities.rdbms;

<<<<<<< HEAD
import com.eshop.app.common.constants.Status;
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
=======
import javax.persistence.*;

>>>>>>> bd701e9 (stage1 : db tables and cassandra connection setup)
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

<<<<<<< HEAD
    private String code;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

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
=======
    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;
>>>>>>> bd701e9 (stage1 : db tables and cassandra connection setup)
}
