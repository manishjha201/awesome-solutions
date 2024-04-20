package com.eshop.app.exception;

import com.eshop.app.common.constants.EShopResultCode;
import lombok.Builder;
import lombok.Data;
import com.eshop.app.utils.Utility;

@Data
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;
    private EShopResultCode resultCode;

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Builder
    public ResourceNotFoundException(EShopResultCode resultCode, String errorMessage) {
        super(Utility.getErrorMsg(resultCode.getMessage(), errorMessage));
        this.errorCode = resultCode.getResultCode();
        this.errorMessage =Utility.getErrorMsg(resultCode.getMessage(), errorMessage);
        this.resultCode = resultCode;
    }


}
