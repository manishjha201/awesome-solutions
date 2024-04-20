package com.eshop.app.common.repositories.rdbms.master;

import com.eshop.app.common.entities.rdbms.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = {"Product", "paymentRequest"})
    Optional<Cart> findById(Long id);

}
