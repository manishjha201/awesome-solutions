package com.eshop.app.common.constants;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum EShopResultCode {

    SUCCESS("00000", "S", "Success"),
    FAILURE("00009", "F", "Failure : "),
    NOT_FOUND("00001", "F", "Entry is not available"),
    INVALID_INPUT("00002", "F", "Invalid input"),
    SYSTEM_ERROR("00003", "F", "Internal error"),
    PRODUCT_STOCK_NOT_AVAILABLE("00003", "F", "Product stock not available at this moment");

    private final String resultCode;
    private final String status;
    private final String message;

    EShopResultCode(String resultCode, String status, String message) {
        this.resultCode = resultCode;
        this.status = status;
        this.message = message;
    }

}
