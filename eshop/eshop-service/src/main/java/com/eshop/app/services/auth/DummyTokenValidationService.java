package com.eshop.app.services.auth;

import com.eshop.app.common.constants.Role;
import com.eshop.app.common.entities.rdbms.User;
import com.eshop.app.constants.CommConstants;
import com.eshop.app.exception.ValidationException;
import com.eshop.app.intercepters.UserContext;
import com.eshop.app.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service("dummy")
@Slf4j
public class DummyTokenValidationService implements ITokenValidationService {

    @Override
    public boolean isValidToken(HttpServletRequest req) throws ValidationException {
        try {
            log.info("validating user token for the request");
            Utility.validateToken(req.getHeader(CommConstants.E_SHOPPING_LOGIN_TOKEN_KEY));
            String esToken = req.getHeader(CommConstants.E_SHOPPING_LOGIN_TOKEN_KEY);
            String loginId = req.getHeader(CommConstants.E_SHOPPING_LOGIN_ID_KEY);
            log.info("token : {}", esToken);
            //TODO : check Cache Service
            if (esToken.equals("12345")) {
                User test = User.builder().id(1L).username("TEST ADMIN").password("fdasfdsf").name("test admin")
                        .email("email.admin@test").loginId(loginId).role(Role.ADMIN).tenantId(1L).isActive(true)
                        .version(1).build();
                UserContext.setUserDetails(test);
                return true;
            } else if(esToken.equals("abcde")) {
                User test = User.builder().id(1L).username("TEST USER").password("dfvfdv").name("test user")
                        .email("email.user@test")
                        .loginId(loginId).role(Role.USER).tenantId(1L).isActive(true).version(1).build();
                UserContext.setUserDetails(test);
                return true;
            }
            User test = User.builder().id(1L).username("TEST USER NA").password("dfvfdv").name("test user")
                    .email("email.na@test")
                    .loginId(loginId).role(Role.USER).tenantId(1L).isActive(true).version(1).build();
            UserContext.setUserDetails(test);
            return true;
            //throw new ValidationException("invalid user");
        } catch(ValidationException e) {
            log.error("Exception occurred ", e);
            throw new ValidationException("invalid user");
        }
    }
}
