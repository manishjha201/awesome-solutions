package com.eshop.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@PropertySource("file:${config.file.path}config.properties")
@EnableAsync
public class SpringAsyncConfig {

    @Value( value = "${eshop.app.concurrency.core.pool.size}")
    private Integer corePoolSize;

    @Value( value = "${eshop.app.concurrency.max.pool.size}")
    private Integer maxPoolSize;

    @Value( value = "${eshop.app.concurrency.queue.capacity}")
    private Integer queueCapacity;

    @Primary
    @Bean( name = "asyncExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("eShoppingChangeEvent-");
        executor.initialize();
        return executor;
    }
}
