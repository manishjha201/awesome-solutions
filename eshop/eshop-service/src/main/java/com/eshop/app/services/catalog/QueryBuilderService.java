package com.eshop.app.services.catalog;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QueryBuilderService implements  IESCustomQueryService {


    @Override
    public BoolQueryBuilder generateESSearchQuery(CatalogSearchQueryDto dto) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolean isAnyFilterAdded = false;
        for (FilterType filterType : FilterType.values()) {
            ESFilter esFilter = ESFilterFactory.getESFilter(filterType);
            BoolQueryBuilder boolQueryBuilder  = esFilter.generateQueryForFilter(dto);
            if (ObjectUtils.isNotEmpty(boolQueryBuilder)) {
                boolQuery.must(boolQueryBuilder);
                isAnyFilterAdded = true;
            }
        }
        return isAnyFilterAdded ? boolQuery : boolQuery.must(QueryBuilders.matchAllQuery()); //TODO : TO TEST MATCH ALL
    }

    private QueryBuilder getQueryForStatus(List<Status> statusList) {
        List<String> statuses = statusList.stream().map(Status::getValue).collect(Collectors.toList());
        return QueryBuilders.multiMatchQuery(String.join(",", statuses))
                .field(SearchConstants.PRODUCT_STATUS_PATH)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
    }

}
