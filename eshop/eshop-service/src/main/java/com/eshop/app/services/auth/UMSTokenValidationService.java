package com.eshop.app.services.auth;

import com.eshop.app.exception.ValidationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service("ums")
public class UMSTokenValidationService  implements ITokenValidationService {


    @Override
    public boolean isValidToken(HttpServletRequest req) throws ValidationException {
        return true;
    }
}
