package com.eshop.app.constants;

import lombok.Getter;

@Getter
public enum SearchConstants {
    TITLE_RAW("title.raw");
    public static final int MAX_RECORDS_TEN_THOUSAND = 10000;
    private final String label;
    SearchConstants(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static final String PRODUCT_PRODUCT_ID_PATH = "productID.keyword"; //TO-TEST
    public static final String PRODUCT_TITLE_PATH = "title.keyword";
    public static final String PRODUCT_NAME_PATH = "name.keyword";
    public static final String PRODUCT_DESCRIPTION_PATH = "description";
    public static final String PRODUCT_STATUS_PATH = "status";
    public static final String CURRENCY_PATH = "currency.keyword";
    public static final String PRODUCT_CATEGORY_STATUS_PATH = "category.status";
    public static final String CATALOG_INDEX = "catalog";
    public static final String DESC = "DESC";
}
