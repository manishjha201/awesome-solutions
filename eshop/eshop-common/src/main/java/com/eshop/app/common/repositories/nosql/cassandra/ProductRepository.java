package com.eshop.app.common.repositories.nosql.cassandra;

import com.eshop.app.common.entities.nosql.cassandra.Catalog;
import com.eshop.app.common.entities.nosql.cassandra.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CassandraRepository<Product, UUID> {
    List<Catalog> findByIsActiveTrue();
}

