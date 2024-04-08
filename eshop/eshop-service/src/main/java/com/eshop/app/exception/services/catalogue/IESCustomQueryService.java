package com.eshop.app.exception.services.catalogue;


import com.eshop.app.models.req.CatalogSearchQueryDto;
import org.elasticsearch.index.query.BoolQueryBuilder;

public interface IESCustomQueryService {
    BoolQueryBuilder buildCustomESQuery(String key, CatalogSearchQueryDto dto);

    BoolQueryBuilder generateESSearchQuery(CatalogSearchQueryDto dto);
}