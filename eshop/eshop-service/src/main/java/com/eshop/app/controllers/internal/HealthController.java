package com.eshop.app.controllers.internal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/catalog/health")
    public String healthCheck() {
        return "OK";
    }
}
