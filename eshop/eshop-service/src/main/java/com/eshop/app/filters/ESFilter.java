package com.eshop.app.filters;

import com.eshop.app.models.req.CatalogSearchQueryDto;
import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;

public abstract class ESFilter {

    public BoolQueryBuilder generateQueryForFilter(CatalogSearchQueryDto dto) {
        CatalogSearchQueryDto updatedDto = ifFilterPresent(dto);
        if(ObjectUtils.isNotEmpty(updatedDto)) {
            return buildQuery(updatedDto);
        }
        return null;
    }

    abstract protected BoolQueryBuilder buildQuery(final CatalogSearchQueryDto dto);
    abstract protected CatalogSearchQueryDto ifFilterPresent(final CatalogSearchQueryDto dto);
}
