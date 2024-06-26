package com.eshop.app.common.constants;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Currency {
    USD("USD", "USD"), INR("INR", "INR");

    private final String code;
    private final String value;

    Currency(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
