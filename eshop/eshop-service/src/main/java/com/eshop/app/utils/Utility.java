package com.eshop.app.utils;

import org.apache.commons.lang3.StringUtils;

public final class Utility {

    private Utility(){}

    public static String getErrorMsg(String message, String errorMessage) {
        return StringUtils.isEmpty(errorMessage) ? message : errorMessage;
    }
}
