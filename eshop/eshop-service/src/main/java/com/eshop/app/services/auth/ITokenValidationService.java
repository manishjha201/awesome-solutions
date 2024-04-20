package com.eshop.app.services.auth;

import com.eshop.app.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;

public interface ITokenValidationService {
    boolean isValidToken(HttpServletRequest req) throws ValidationException;
}
