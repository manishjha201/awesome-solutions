package com.eshop.app.common.constants;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    INITIATED("INITIATED"),  PENDING("PENDING"), IN_PROGRESS("IN_PROGRESS"), COMPLETED("COMPLETED"), REVERSED("REVERSED");
    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
