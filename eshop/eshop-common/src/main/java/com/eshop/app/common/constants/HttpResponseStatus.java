package com.eshop.app.common.constants;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum HttpResponseStatus implements Serializable {

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
