package com.eshop.app.common.constants;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE"), DELETED("DELETED");
    private final String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
