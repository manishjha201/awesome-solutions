package com.eshop.app.exception.services.catalogue;

import com.eshop.app.common.constants.CatalogSearchKey;
import com.eshop.app.common.constants.Status;
import com.eshop.app.constants.FilterType;
import com.eshop.app.constants.SearchConstants;
import com.eshop.app.factory.ESFilterFactory;
import com.eshop.app.filters.ESFilter;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.search.MatchQueryParser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ESCustomQueryService implements  IESCustomQueryService {

    @Override
    public BoolQueryBuilder buildCustomESQuery(String key, CatalogSearchQueryDto dto) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Boolean flag = true;
        if (ObjectUtils.isNotEmpty(dto.getSearchValue())) {
            boolQueryBuilder.must(getQueryForSearchKey(key, dto.getSearchValue()));
            flag = false;
        }
        if (ObjectUtils.isNotEmpty(dto.getStatusList())) {
            boolQueryBuilder.must(getQueryForStatus(dto.getStatusList()));
            flag = false;
        }
        if(flag) {
            MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
            boolQueryBuilder = boolQueryBuilder.must(matchAllQueryBuilder);
        }
        return boolQueryBuilder;
    }

    @Override
    public BoolQueryBuilder generateESSearchQuery(CatalogSearchQueryDto dto) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        boolean isAnyFilterAdded = false;
        for(FilterType filterType : FilterType.values()) {
            ESFilter esFilter = ESFilterFactory.getESFilter(filterType);
            BoolQueryBuilder boolQueryBuilder  = esFilter.generateQueryForFilter(dto);
            if(ObjectUtils.isNotEmpty(queryBuilder)) {
                boolQueryBuilder.must(queryBuilder);
                isAnyFilterAdded = true;
            }
        }
        return isAnyFilterAdded ? queryBuilder : queryBuilder.must(QueryBuilders.matchAllQuery());
    }

    private QueryBuilder getQueryForStatus(List<Status> statusList) {
        List<String> statuses = statusList.stream().map(Status::getValue).collect(Collectors.toList());
        return QueryBuilders.multiMatchQuery(String.join(",", statuses))
                .field(SearchConstants.PRODUCT_STATUS_PATH)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
    }

    private BoolQueryBuilder getQueryForSearchKey(String key, String searchValue) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        MultiMatchQueryBuilder multiMatchQueryBuilder = null;

        if (ObjectUtils.isEmpty(key)) {
            return boolQuery;
        }

        if (key.equals(CatalogSearchKey.TITLE)) {
            multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchValue).field(SearchConstants.PRODUCT_TITLE_PATH).type(MatchQueryParser.Type.PHRASE_PREFIX);
            return boolQuery.must(multiMatchQueryBuilder);
        }

        if (key.equals(CatalogSearchKey.DESCRIPTION)) {
            multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchValue)
                    .field(SearchConstants.PRODUCT_DESCRIPTION_PATH)
                    .type(MatchQueryParser.Type.PHRASE_PREFIX);
            return boolQuery.must(multiMatchQueryBuilder);
        }
        //ANY
        multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchValue)
                .field(SearchConstants.PRODUCT_TITLE_PATH)
                .field(SearchConstants.PRODUCT_TITLE_PATH)
                .type(MatchQueryParser.Type.PHRASE_PREFIX);
        return boolQuery.must(multiMatchQueryBuilder);
    }
}
