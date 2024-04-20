package com.eshop.app.factory;

import com.eshop.app.constants.FilterType;
import com.eshop.app.filters.ESFilter;
import com.eshop.app.filters.ProductStatusFilter;
import com.eshop.app.filters.TextFilter;

public class ESFilterFactory {

    public static ESFilter getESFilter(FilterType filterType) {
        switch (filterType) {
            case STATUS_FILTER: return new ProductStatusFilter();
            default:
                return new TextFilter();
        }
    }
}
