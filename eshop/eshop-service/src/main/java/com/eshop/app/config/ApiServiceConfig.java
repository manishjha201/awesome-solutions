package com.eshop.app.config;

import com.eshop.app.models.resp.ProductResp;
import com.eshop.app.models.resp.TenantResp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import com.eshop.app.services.DataService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.Function;

@Configuration
public class ApiServiceConfig {

    @Bean("restConnectionTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Map<Class<?>, String> apiUrls() {
        return Map.of(
                ProductResp.class, "http://localhost:8080/internal/data/v1/products/",
                TenantResp.class, "http://localhost:8080/internal/data/v1/catalog/tenants/"
                // Add other classes and URLs as needed
        );
    }

    @Bean
    public Function<Object, Object> identityFunction() {
        return Function.identity();
    }

    @Bean("productClientService")
    public DataService<ProductResp, ProductResp> productClientService(@Qualifier("restConnectionTemplate") RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate) {
        Function<ProductResp, ProductResp> identity = Function.identity();
        return new DataService<>(restTemplate, redisTemplate, identity, apiUrls());
    }

    @Bean("tenantClientService")
    public DataService<TenantResp, TenantResp> tenantClientService(@Qualifier("restConnectionTemplate") RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate) {
        Function<TenantResp, TenantResp> identity = Function.identity();
        return new DataService<>(restTemplate, redisTemplate, identity, apiUrls());
    }
}
