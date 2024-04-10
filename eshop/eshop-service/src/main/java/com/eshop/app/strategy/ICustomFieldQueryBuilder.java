package com.eshop.app.strategy;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface ICustomFieldQueryBuilder {
    BoolQueryBuilder prepare(String queryString);
}
