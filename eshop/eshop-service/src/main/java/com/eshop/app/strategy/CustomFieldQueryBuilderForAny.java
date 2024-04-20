package com.eshop.app.strategy;

import com.eshop.app.constants.SearchConstants;
import lombok.NoArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

@NoArgsConstructor
public final class CustomFieldQueryBuilderForAny implements ICustomFieldQueryBuilder {

    @Override
    public BoolQueryBuilder prepare(String queryString) {
        final BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder queryBuilderForTitle = QueryBuilders.queryStringQuery(queryString)
                .field(SearchConstants.PRODUCT_TITLE_PATH)
                .field(SearchConstants.PRODUCT_NAME_PATH)
                .field(SearchConstants.PRODUCT_PRODUCT_ID_PATH)
                .defaultOperator(Operator.AND);
        boolQueryBuilder.should(queryBuilderForTitle);
        return queryBuilder.should(boolQueryBuilder);
    }
}
