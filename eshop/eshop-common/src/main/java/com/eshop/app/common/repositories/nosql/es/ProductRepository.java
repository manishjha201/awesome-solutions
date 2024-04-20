package com.eshop.app.common.repositories.nosql.es;

import com.eshop.app.common.entities.nosql.es.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.elasticsearch.annotations.Query;


@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    @Query("{\"wildcard\": {\"name.keyword\": \"?0\"}}")
    Page<Product> findByNameWildcard(String wildcardPattern, Pageable pageable);

    @Query("{\"match\": {\"name\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    Page<Product> findByNameFuzzy(String searchText, Pageable pageable);

    @Query("{\"wildcard\": {\"title.keyword\": \"?0\"}}")
    Page<Product> findByTitleWildcard(String wildcardPattern, Pageable pageable);

    @Query("{\"wildcard\": {\"title.keyword\": \"?0\"}}")
    Page<Product> findByTitleFuzzy(String wildcardPattern, Pageable pageable);

}
