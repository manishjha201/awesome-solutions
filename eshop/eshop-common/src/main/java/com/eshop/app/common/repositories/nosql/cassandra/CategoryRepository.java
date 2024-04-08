package com.eshop.app.common.repositories.nosql.cassandra;

import com.eshop.app.common.entities.nosql.Category;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CassandraRepository<Category, UUID> {
    List<Category> findByIsActiveTrue();
}
