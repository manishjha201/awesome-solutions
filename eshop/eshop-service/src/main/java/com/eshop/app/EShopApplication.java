package com.eshop.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class EShopApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(EShopApplication.class, args);
        initializeApp(ctx);
    }

    private static void initializeApp(ApplicationContext ctx) {
        // do nothing
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
