package com.eshop.app.common.constants;

import lombok.Getter;

@Getter
public enum TenantType {
    INTERNAL("INTERNAL"), EXTERNAL("EXTERNAL");

    private final String label;
    TenantType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
