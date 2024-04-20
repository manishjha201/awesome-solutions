package com.eshop.app.utils;

import com.eshop.app.common.constants.CatalogSearchKey;
import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.entities.rdbms.CartProduct;
import com.eshop.app.exception.ValidationException;
import com.eshop.app.models.req.*;
import org.apache.commons.lang3.ObjectUtils;

public final class ValidateInputRequestHelper {

    private static final String SAFE_TEXT_PATTERN = "^[\\w\\s.,-]*$";

    public static boolean validateCatalogSearchQuery(CatalogSearchQueryDto dto) {
        if (!isValidKeyForCatalogSearchQuery(dto.getSearchKey())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more keys used are invalid or not allowed to be updated.");
        }

        if (!isSafeText(dto.getSearchValue())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more keys or values used are invalid or not allowed to be updated.");
        }

        if (!isSafeText(dto.getSortBy())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more keys or values used are invalid or not allowed to be updated.");
        }

        return true;
    }

    public static boolean isSafeText(String text) {
        return ObjectUtils.isEmpty(text) ? Boolean.TRUE :  text.matches(SAFE_TEXT_PATTERN);
    }


    public static boolean isValidKeyForCatalogSearchQuery(String input) {
        for (CatalogSearchKey key : CatalogSearchKey.values()) {
            if (key.getLabel().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateProductDTOReq(ProductReqDTO dto) {
        if (!isSafeText(dto.getName())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }

        if (!isSafeText(dto.getTitle())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }

        if (!isSafeText(dto.getDescription())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }

        return true;
    }

    public static boolean validateReqSize(int size) {
        if (size > 10000) { //TODO : make it configurable
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "input request is more that max size limit allowed");
        }
        return Boolean.TRUE;
    }

    public static boolean validateProductUpdateDTOReq(ProductUpdateReqDTO dto) {
        if (!isSafeText(dto.getName())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }

        if (!isSafeText(dto.getTitle())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }

        if (!isSafeText(dto.getDescription())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }

        if (ObjectUtils.isEmpty(dto.getVersionNo()) || dto.getVersionNo() < 0) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }

        return true;
    }

    public static boolean validateUserReq(UserLoginReqDTO dto) {
        if (!isSafeText(dto.getName())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }
        if (!isSafeText(dto.getUsername())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }
        if (!isSafeText(dto.getEmail())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }
        if (!isSafeText(dto.getLoginId())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }
        return true;
    }

    public static boolean isValidCartProductReq(CartProduct cartItem) {
        return true; //TODO : pending
    }

    public static boolean validatePaymentRequest(CartPaymentReq dto) {
        if (!isSafeText(dto.getPaymentChannel())) {
            throw new ValidationException(EShopResultCode.INVALID_INPUT, "One or more vales or values used are invalid or not allowed to be updated.");
        }
        return true;
    }
}
