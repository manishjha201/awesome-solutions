package com.eshop.app.services;


import com.eshop.app.models.req.CatalogSearchQueryDto;
import org.elasticsearch.index.query.BoolQueryBuilder;

public interface IESCustomQueryService {
    BoolQueryBuilder generateESSearchQuery(CatalogSearchQueryDto dto);
}