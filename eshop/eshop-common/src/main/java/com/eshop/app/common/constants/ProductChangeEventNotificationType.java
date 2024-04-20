package com.eshop.app.common.constants;

import lombok.Getter;

@Getter
public enum ProductChangeEventNotificationType {
    LOW_IN_VOLUME("LOW_IN_INVENTORY");

    private final String label;
    ProductChangeEventNotificationType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
