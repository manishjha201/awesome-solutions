package com.eshop.app.common.constants;

import lombok.Getter;

@Getter
public enum CatalogSearchKey {
    TITLE("title"), NAME("name"), PRODUCT_ID("product-id") , ANY("any");

    private final String label;
    private CatalogSearchKey(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
