package com.eshop.app.controllers.external;

import com.eshop.app.models.req.UserLoginReqDTO;
import com.eshop.app.services.IValidationService;
import com.eshop.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IValidationService validationService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserLoginReqDTO userReqDto) {
        userService.register(userReqDto);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateLogin( @RequestHeader(value = "loginId", required = false) String loginId,
                                                  @RequestHeader(value = "estoken", required = false) String esToken,
                                                  @Valid @RequestBody UserLoginReqDTO userReqDto) {
        validationService.validate(userReqDto);
        boolean isValid = userService.validateUser(userReqDto);
        return ResponseEntity.ok(isValid);
    }
}
