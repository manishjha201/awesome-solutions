package com.eshop.app.common.entities.rdbms;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "catalog")
@NamedEntityGraph(
        name = "catalog-with-products",
        attributeNodes = @NamedAttributeNode("products")
)
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "catalog", fetch = FetchType.LAZY)
    private List<Product> products;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    public void initializeProducts() {
        products.size(); //lazy loading
    }

    /*
    @Size(max = 64)
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    //@CreatedDate
    private Date createdAt;

    @Size(max = 64)
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "last_updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    //@LastModifiedDate
    private Date lastUpdatedAt;
    */
}
