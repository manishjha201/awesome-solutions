package com.eshop.app.common.constants;

import lombok.Getter;

@Getter
public enum HttpResponseStatus {
    FAIL("FAIL"), ACCEPTED("ACCEPTED"), UPDATED("UPDATED"), DELETED("DELETED");
    private final String value;

    HttpResponseStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
