package com.eshop.app.consumer.task.manager;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class RecordManager {

    private static CircuitBreakerBasedThreadPoolExecutor executor = null;

    private static ThreadExecutorConfig config;

    private static volatile boolean isStarted = false;

    private static volatile boolean isShutdown;

    public static boolean isStarted() {
        return isStarted;
    }

    public static boolean isShutdown() {
        return isShutdown;
    }


    //TODO : make it configurable
    private static final int corePoolSize = 10;
    private static final int maxPoolSize = 100;
    private static final int keepAliveTimeInMs = 6000;
    private static final  int capacity = 100000;

    private RecordManager(){}

    public static void start() {
        ThreadExecutorConfig config = ThreadExecutorConfig.builder().corePoolSize(corePoolSize)
                .maximumPoolSize(maxPoolSize).keepAliveTime(keepAliveTimeInMs).unit(TimeUnit.MILLISECONDS).workQueue(new LinkedBlockingQueue<>(capacity)).build();
        executor = new CircuitBreakerBasedThreadPoolExecutor(config);
        isStarted = true;
    }

    public static <T>  CompletableFuture<T> submitPayloadRequest(Callable<T> task) {
        synchronized (RecordManager.class) {
            if (isShutdown) {
                throw new IllegalStateException("illegal state, task is submitted during shutdown");
            }
            if(!isStarted) {
                start();
            }
        }
        CompletableFuture<T> future = new CompletableFuture<>();
        executor.submit(() -> {
            try {
                T result = task.call();
                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }
}
