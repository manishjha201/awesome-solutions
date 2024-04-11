package com.eshop.app.factory;

import com.eshop.app.constants.CommConstants;
import com.eshop.app.services.auth.ITokenValidationService;

import java.util.HashMap;
import java.util.Map;

public class TokenValidationFactory {
    private final Map<String, ITokenValidationService> tokenValidationServiceMap;
    private final ITokenValidationService defaultTokenValidationService;

    public TokenValidationFactory(ITokenValidationService dummyTokenValidationService,
            ITokenValidationService defaultTokenValidationService) {
        this.defaultTokenValidationService = defaultTokenValidationService;
        tokenValidationServiceMap = new HashMap<>();
        tokenValidationServiceMap.put(
                CommConstants.UMS_TOKEN_VALIDATOR, defaultTokenValidationService);
        tokenValidationServiceMap.put(CommConstants.DUMMY_TOKEN_VALIDATOR, dummyTokenValidationService);
    }

    public ITokenValidationService get(String key) {
        return tokenValidationServiceMap.getOrDefault(key, defaultTokenValidationService);
    }
}
