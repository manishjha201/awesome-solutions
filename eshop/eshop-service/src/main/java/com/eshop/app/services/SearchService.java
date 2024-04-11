package com.eshop.app.services;

import com.eshop.app.common.repositories.nosql.es.ProductRepository;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.utils.Utility;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import com.eshop.app.common.entities.nosql.es.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService implements ISearchService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ElasticsearchOperations searchOperation;

    @Autowired
    private IESCustomQueryService queryBuilderService;

    @Override
    public Page<Product> customQueryBuilderProductSearch(String searchText, Pageable pageable) {
        String[] keywords = searchText.split(" ");
        QueryBuilder queryBuilder = QueryBuilders.boolQuery();
        for (String keyword : keywords) {
            ((BoolQueryBuilder) queryBuilder).should(
                    QueryBuilders.multiMatchQuery(keyword, "title", "name", "description", "tags")
            );
        }
        Query searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        return Utility.listToPage(searchOperation.search(searchQuery, Product.class).stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList()), pageable);
    }

    @Override
    public Page<Product> searchByCustomQueryBuilder(CatalogSearchQueryDto dto, Pageable pageable) {
        QueryBuilder queryBuilder = queryBuilderService.generateESSearchQuery(dto);
        Query searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        SearchHits<Product> searchHits = searchOperation.search(searchQuery, Product.class);
        List<Product> productList = searchHits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
        return new PageImpl<>(productList, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<Product> searchProducts(String searchText, Pageable pageable) { //TO-TEST
        String wildCard = "*eco*";
        Page<Product> results = productRepository.findByTitleWildcard(searchText, pageable);
        return results;
    }

}

