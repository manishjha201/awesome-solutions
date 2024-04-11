package com.eshop.app.common.repositories.rdbms.slave;

import com.eshop.app.common.entities.rdbms.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "eShopSlaveTransactionManager", readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
