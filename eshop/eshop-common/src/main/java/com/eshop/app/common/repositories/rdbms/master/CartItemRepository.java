package com.eshop.app.common.repositories.rdbms.master;

import com.eshop.app.common.entities.rdbms.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional("eShopMasterTransactionManager")
@Repository
public interface CartItemRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByUserId(Long userId);
}
