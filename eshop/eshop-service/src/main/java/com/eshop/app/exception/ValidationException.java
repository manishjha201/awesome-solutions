package com.eshop.app.exception;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.utils.Utility;
import lombok.Builder;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;
    private EShopResultCode resultCode;

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message) {
        super(message);
    }

    @Builder
    public ValidationException(EShopResultCode resultCode, String errorMessage) {
        super(Utility.getErrorMsg(resultCode.getMessage(), errorMessage));
        this.errorCode = resultCode.getResultCode();
        this.errorMessage = Utility.getErrorMsg(resultCode.getMessage(), errorMessage);
        this.resultCode = resultCode;
    }
}
