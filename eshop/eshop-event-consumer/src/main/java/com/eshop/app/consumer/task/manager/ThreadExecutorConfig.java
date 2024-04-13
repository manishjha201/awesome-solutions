package com.eshop.app.consumer.task.manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreadExecutorConfig {
    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAliveTime;
    private TimeUnit unit;
    private LinkedBlockingQueue<Runnable> workQueue;
}
