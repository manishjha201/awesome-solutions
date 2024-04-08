package com.eshop.app.common.constants;

import lombok.Getter;

@Getter
public enum CatalogSearchKey {
    TITLE("TITLE"), DESCRIPTION("DESCRIPTION"), ANY("ANY");

    private final String label;
    private CatalogSearchKey(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
