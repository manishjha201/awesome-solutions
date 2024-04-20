package com.eshop.app.common.repositories.rdbms.slave;

import com.eshop.app.common.entities.rdbms.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "eShopSlaveTransactionManager", readOnly = true)
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
}
