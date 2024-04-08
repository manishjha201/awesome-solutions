package com.eshop.app.constants;

import lombok.Getter;

@Getter
public enum FilterType {
    TEXT_FILTER("textFilter"), STATUS_FILTER("statusFilter");
    private final String label;
    private FilterType(String label) {
        this.label = label;
    }
}
