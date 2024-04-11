package com.eshop.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EntityScan("com.eshop.app.common.entities.rdbms")
@ComponentScan(basePackages = {"com.eshop.app", "com.eshop.app.common"})
public class EShopApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(EShopApplication.class, args);
        initializeApp(ctx);
        AbstractApplicationContext appCtx = ((AbstractApplicationContext) ctx);
        appCtx.registerShutdownHook();
        log.info("Server started successfully as per logs");
    }

    private static void initializeApp(ApplicationContext ctx) {
        //TODO : enable write back caching
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
