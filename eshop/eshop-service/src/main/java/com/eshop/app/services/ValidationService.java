package com.eshop.app.services;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.exceptions.BusinessException;
import com.eshop.app.intercepters.UserContext;
import com.eshop.app.models.req.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ValidationService implements  IValidationService {

    @Override
    public boolean validate(HttpRequest httpReq) {
        if(ObjectUtils.isNotEmpty(httpReq)) {
            httpReq.validate();
        }
        return true;
    }

    @Override
    public void validateToken(String esToken) {
        if (ObjectUtils.isEmpty(UserContext.getUserDetail())) throw new BusinessException(EShopResultCode.INVALID_INPUT.getResultCode());
    }
}
