package com.eshop.app.common.constants;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Status {
    ACTIVE("Active"), INACTIVE("Inactive"), DELETED("Deleted");
    private final String value;

    Status(String value) {
        this.value = value;
    }
}
