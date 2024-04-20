package com.eshop.app.common.constants;

import lombok.Getter;
    private final String message;

    EShopResultCode(String resultCode, String status, String message) {
        this.resultCode = resultCode;
        this.status = status;
        this.message = message;
    }

}
