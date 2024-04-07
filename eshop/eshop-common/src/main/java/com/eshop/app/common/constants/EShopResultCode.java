package com.eshop.app.common.constants;

import lombok.Getter;

@Getter
public enum EShopResultCode {

    SUCCESS("00000", "S", "Success"),
    NOT_FOUND("00001", "F", "Entry is not available");

    private final String resultCode;
    private final String status;
    private final String message;

    EShopResultCode(String resultCode, String status, String message) {
        this.resultCode = resultCode;
        this.status = status;
        this.message = message;
    }

}
