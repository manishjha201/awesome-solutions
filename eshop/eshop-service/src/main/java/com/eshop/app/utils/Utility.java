package com.eshop.app.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utility {

    public static String getErrorMsg(String message, String errorMessage) {
        return StringUtils.isEmpty(errorMessage) ? message : errorMessage;
    }
}
