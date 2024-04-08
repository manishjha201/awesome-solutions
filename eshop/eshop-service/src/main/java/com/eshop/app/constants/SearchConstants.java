package com.eshop.app.constants;

import lombok.Getter;

@Getter
public enum SearchConstants {
    TITLE_RAW("title.raw");
    private final String label;
    private SearchConstants(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static final String PRODUCT_TITLE_PATH = "title";
    public static final String PRODUCT_DESCRIPTION_PATH = "description";
    public static final String PRODUCT_STATUS_PATH = "isActive";
    public static final String CATALOG_INDEX = "catalog";
    public static final String DESC = "DESC";
}
