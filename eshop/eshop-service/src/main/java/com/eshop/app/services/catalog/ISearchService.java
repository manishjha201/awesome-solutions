package com.eshop.app.services.catalog;

import com.eshop.app.common.entities.nosql.es.Product;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISearchService {
   Page<Product> customQueryBuilderProductSearch(String searchText, Pageable pageable);
   Page<Product> searchProducts(String searchText, Pageable pageable);

   //TODO  : TEST
   Page<Product> searchByCustomQueryBuilder(CatalogSearchQueryDto dto, Pageable pageable);
}
