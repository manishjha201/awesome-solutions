package com.eshop.app.filters;

import com.eshop.app.common.constants.CatalogSearchKey;
import com.eshop.app.factory.CustomFieldQueryBuilderFactory;
import com.eshop.app.models.req.CatalogSearchQueryDto;
import com.eshop.app.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;

import java.util.List;
import java.util.stream.Collectors;

public class TextFilter extends ESFilter {

    private static final List<String> whiteListedFilters = List.of(CatalogSearchKey.TITLE, CatalogSearchKey.NAME, CatalogSearchKey.PRODUCT_ID, CatalogSearchKey.ANY).stream().map(a-> a.toString()).collect(Collectors.toList());

    @Override
    protected BoolQueryBuilder buildQuery(CatalogSearchQueryDto dto) {
        String[] searchValues = dto.getSearchValue().split(",");
        String queryString = getQueryString(searchValues);
        return  new CustomFieldQueryBuilderFactory().get(dto.getSearchKey()).prepare(queryString);
    }

    private String getQueryString(String[] searchValues) {
        String queryString = "";
        for (String filterText : searchValues) {
            filterText = filterText.trim();
            String[] searchValueBySpace = filterText.split("\\s+");
            for(String value : searchValueBySpace) {
                String enhancedString = Utility.enhanceInput(value);
                queryString = queryString.concat("*").concat(enhancedString).concat("* ");
            }
        }
        return queryString;
    }

    @Override
    protected CatalogSearchQueryDto ifFilterPresent(CatalogSearchQueryDto dto) {
        if (StringUtils.isEmpty(dto.getSearchValue())) {
            return null;
        }
        if (whiteListedFilters.contains(dto.getSearchKey())) {
            return dto;
        }
        return null;
    }
}
