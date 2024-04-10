package com.eshop.app.factory;

import com.eshop.app.strategy.ICustomFieldQueryBuilder;

public interface ICustomFieldQueryBuilderFactory {
    ICustomFieldQueryBuilder get(String searchKey);
}
