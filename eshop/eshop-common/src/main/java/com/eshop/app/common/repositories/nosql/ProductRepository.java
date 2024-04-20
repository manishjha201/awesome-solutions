package com.eshop.app.common.repositories.nosql;

import com.eshop.app.common.entities.nosql.Catalog;
import com.eshop.app.common.entities.nosql.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CassandraRepository<Product, UUID> { //TODO : Add pagination support for PagingAndSortingRepository<Product, UUID>
    //TODO : custom query methods as needed
    List<Catalog> findByIsActiveTrue();
}

