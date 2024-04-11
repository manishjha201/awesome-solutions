package com.eshop.app.services;

import com.eshop.app.models.req.HttpRequest;

public interface IValidationService {
    boolean validate(HttpRequest httpReq);
}
