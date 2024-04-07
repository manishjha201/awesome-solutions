package com.eshop.app.common.repositories.rdbms;

import com.eshop.app.common.entities.rdbms.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

}
