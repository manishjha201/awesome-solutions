package com.eshop.app.common.repositories.nosql.cassandra;

import com.eshop.app.common.entities.nosql.cassandra.Catalog;
import org.springframework.stereotype.Repository;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CatalogRepository extends CassandraRepository<Catalog, UUID> { //TODO : Add pagination support PagingAndSortingRepository<Catalog, UUID>
    //TODO : Add custom query methods if needed
    List<Catalog> findByIsActiveTrue();
}

