package com.eshop.app.factory;

import com.eshop.app.common.constants.CatalogSearchKey;
import com.eshop.app.constants.SearchConstants;
import com.eshop.app.strategy.*;

import java.util.HashMap;
import java.util.Map;

public class CustomFieldQueryBuilderFactory implements ICustomFieldQueryBuilderFactory {
    private final Map<String, ICustomFieldQueryBuilder> holder;
    private final ICustomFieldQueryBuilder defaultValue;
    public CustomFieldQueryBuilderFactory() {
        holder = new HashMap<>();
        defaultValue = new CustomFieldQueryBuilderForAny();
        holder.put(CatalogSearchKey.TITLE.toString(), new CustomFieldQueryBuilderForTitle());
        holder.put(CatalogSearchKey.NAME.toString(), new CustomFieldQueryBuilderForName());
        holder.put(CatalogSearchKey.PRODUCT_ID.toString(), new CustomFieldQueryBuilderForProductId());
    }

    @Override
    public ICustomFieldQueryBuilder get(String searchKey) {
        return holder.getOrDefault(searchKey.trim().toLowerCase(), defaultValue);
    }
}
