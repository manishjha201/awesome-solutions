package com.eshop.app.controllers.external;

import com.eshop.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String password) {
        userService.registerUser(username, password);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateLogin(@RequestParam String username, @RequestParam String password) {
        boolean isValid = userService.validatePassword(username, password);
        return ResponseEntity.ok(isValid);
    }
}
