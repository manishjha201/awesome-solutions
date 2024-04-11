package com.eshop.app.common.repositories.rdbms.master;

import com.eshop.app.common.entities.rdbms.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("eShopMasterTransactionManager")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

