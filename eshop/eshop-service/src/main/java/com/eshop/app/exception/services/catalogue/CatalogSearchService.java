package com.eshop.app.exception.services.catalogue;

import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.constants.SearchConstants;
import com.eshop.app.exception.ResourceNotFoundException;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.models.resp.SearchCatalogResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public final class CatalogSearchService implements ICatalogSearchService {

    private final RestHighLevelClient client;
    private final IESCustomQueryService queryService;

    public CatalogSearchService(RestHighLevelClient client, IESCustomQueryService queryService) {
        this.client = client;
        this.queryService = queryService;
    }

    @Override
    public SearchCatalogResponse getCatalogDetails( CatalogSearchQueryDto dto, String loginId) throws ResourceNotFoundException, BusinessException {
        log.info("fetching detail from Es data store for loginUser : {} ", loginId);
        //TODO : Add DAO layer
        BoolQueryBuilder searchQueryBuilder = queryService.generateESSearchQuery(dto);
        return createResponse(searchQueryBuilder, dto, loginId);
    }

    private SearchCatalogResponse createResponse(BoolQueryBuilder queryBuilder, CatalogSearchQueryDto dto, String loginId) {
        SearchCatalogResponse resp = null;
        try {
            //TODO : Add DAO layer
            SearchResponse response = executeESQuery(queryBuilder, dto);
            SearchHit[] searchHits = response.getHits().getHits();
            //List<Product> products = Arrays.stream(searchHits).map(hit -> JSON.parseObject(hit.getSourceAsString(), Product.class)).collect(Collectors.toList());

        } catch(Exception e) { //TODO : DO Exception Mapping

        }

        return null;
    }

    private SearchResponse executeESQuery(BoolQueryBuilder queryBuilder, CatalogSearchQueryDto dto) {
        try {
            SearchRequest searchRequest = new SearchRequest(SearchConstants.CATALOG_INDEX);
            searchRequest.source(buildSearchBuilder(queryBuilder, dto));
            return client.search(searchRequest, RequestOptions.DEFAULT);
        } catch(IOException e) {
            throw new BusinessException(e.getCause());
        }
    }

    private SearchSourceBuilder buildSearchBuilder(BoolQueryBuilder queryBuilder, CatalogSearchQueryDto dto) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.trackTotalHits(true);
        int from = (dto.getPageNumber() - 1) * dto.getPageSize();
        int size = dto.getPageSize();
        if (ObjectUtils.isEmpty(queryBuilder)) return sourceBuilder;
        sourceBuilder.query(queryBuilder);
        if (ObjectUtils.isNotEmpty(buildSortBuilder(dto))) {
            sourceBuilder.sort(buildSortBuilder(dto));
        }
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        return sourceBuilder;
    }

    private FieldSortBuilder buildSortBuilder(CatalogSearchQueryDto dto) {
        FieldSortBuilder sortBuilder = null;
        String[] sort;
        String sortBy = null;
        String sortOrder = null;
        if(StringUtils.isEmpty(dto.getSortBy())) {
            sort = dto.getSortBy().split(":");
            if(sort.length == 2) {
                sortBy = sort[0];
                sortOrder = sort[1];
            } else {
                sortBy = sort[0];
            }
        }

        if (sortOrder != null && sortOrder.equalsIgnoreCase(SearchConstants.DESC)) {
           log.info("sorting in DESC...");
           sortBuilder = SortBuilders.fieldSort(SearchConstants.TITLE_RAW.getLabel()).order(SortOrder.DESC);
        } else if(sortBy != null) {
            sortBuilder = SortBuilders.fieldSort(SearchConstants.TITLE_RAW.getLabel()).order(SortOrder.ASC);
        } else {
            sortBuilder = SortBuilders.fieldSort(SearchConstants.TITLE_RAW.getLabel()).order(SortOrder.ASC);
        }
        return sortBuilder;
    }
}
