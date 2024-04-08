package com.eshop.app.filters;

import com.eshop.app.common.constants.CatalogSearchKey;
import com.eshop.app.constants.SearchConstants;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;

import java.util.List;
import java.util.stream.Collectors;

public class TextFilter extends ESFilter {

    private static final List<String> availableFilters = List.of(CatalogSearchKey.TITLE, CatalogSearchKey.DESCRIPTION).stream().map(a-> a.toString()).collect(Collectors.toList());

    @Override
    protected BoolQueryBuilder buildQuery(CatalogSearchQueryDto dto) {
        if (CatalogSearchKey.TITLE.toString().equals(dto.getSearchKey())) return buildTitleSearchQuery(dto);
        return null;
    }

    private BoolQueryBuilder buildTitleSearchQuery(CatalogSearchQueryDto dto) {
        final BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String[] searchValues = dto.getSearchValue().split(",");
        String queryString = getQueryString(searchValues);
        QueryBuilder queryBuilderForTitle = QueryBuilders.queryStringQuery(queryString).field(SearchConstants.PRODUCT_TITLE_PATH)
                .defaultOperator(Operator.AND);
        boolQueryBuilder.should(queryBuilderForTitle);
        queryBuilder.should(boolQueryBuilder);
        return queryBuilder;
    }

    private String getQueryString(String[] searchValues) {
        String queryString = "";
        for (String filterText : searchValues) {
            filterText = filterText.trim();
            String[] searchValueBySpace = filterText.split("\\s+");
            for(String value : searchValueBySpace) {
                queryString = queryString.concat("*").concat(Utility.enhanceInput(value).concat("* "));
            }
        }
        return queryString;
    }

    @Override
    protected CatalogSearchQueryDto ifFilterPresent(CatalogSearchQueryDto dto) {
        if (StringUtils.isEmpty(dto.getSearchValue())) {
            return null;
        }
        if (availableFilters.contains(dto.getSearchKey())) {
            return dto;
        }
        dto.setSearchKey(CatalogSearchKey.TITLE.getLabel());
        return dto;
    }
}
