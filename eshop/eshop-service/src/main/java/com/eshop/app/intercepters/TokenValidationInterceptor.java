package com.eshop.app.intercepters;

import com.eshop.app.exception.ValidationException;
import com.eshop.app.factory.TokenValidationFactory;
import com.eshop.app.services.auth.ITokenValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class TokenValidationInterceptor implements HandlerInterceptor {

    private final ITokenValidationService tokenValidationService;
    private final ITokenValidationService dummyTokenValidationService;

    private TokenValidationInterceptor(@Qualifier("ums") ITokenValidationService tokenValidationService, @Qualifier("dummy") ITokenValidationService dummyTokenValidationService) {
        this.dummyTokenValidationService = dummyTokenValidationService;
        this.tokenValidationService = tokenValidationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ValidationException {
        return new TokenValidationFactory(dummyTokenValidationService, tokenValidationService).get("dummy").isValidToken(request);
    }

}
