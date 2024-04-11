package com.eshop.app.services;

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
}
