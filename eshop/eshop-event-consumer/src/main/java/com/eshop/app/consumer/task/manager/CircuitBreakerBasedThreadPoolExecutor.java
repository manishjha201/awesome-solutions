package com.eshop.app.consumer.task.manager;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class CircuitBreakerBasedThreadPoolExecutor  extends ThreadPoolExecutor {

    private IFeedbackTracker feedbackTracker;
    private AtomicReference<ExecutorState> currentState; //TODO
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private static final long timeout = 1000;
    private final AtomicLong lastModifiedAt;
    private final AtomicReference<ChannelStatus> lastChannelStatus;


    public CircuitBreakerBasedThreadPoolExecutor(ThreadExecutorConfig config) {
        super(config.getCorePoolSize(), config.getMaximumPoolSize(), config.getKeepAliveTime(), config.getUnit(),
                config.getWorkQueue(), new DiscardOldestPolicy());
        this.feedbackTracker = new FeedbackTracker(1000);
        this.currentState = new AtomicReference<>(ExecutorState.CLOSED);
        this.lastModifiedAt = new AtomicLong(System.currentTimeMillis());
        this.lastChannelStatus = new AtomicReference<>(ChannelStatus.GREEN);
    }

    public <T> Future<T> submit(Callable<T> task) {
        ChannelStatus cStatus = feedbackTracker.getChannelStatus();
        try {
            if(lock.tryLock(timeout , TimeUnit.MILLISECONDS)) {
                try {
                    if(cStatus != lastChannelStatus.get()) {
                        if(cStatus == ChannelStatus.GREEN) {
                            this.currentState.set(ExecutorState.CLOSED);
                        }

                        if(cStatus == ChannelStatus.RED) {
                            this.currentState.set(ExecutorState.OPEN);
                        }
                    }
                    ExecutorState cNowState = this.currentState.get();
                    if (cNowState == ExecutorState.OPEN  && isTimeElapsed(this.lastModifiedAt.get())) {
                        this.condition.signalAll(); // CHANNEL IS GREEN AGAIN
                    } else {
                        throw new RuntimeException("Channel for RPC is down. please try after sometime");
                    }
                    if (cNowState == ExecutorState.CLOSED ) {
                        this.condition.signalAll(); // CHANNEL IS GREEN AGAIN
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return super.submit(() -> {
            try {
                T result = task.call();
                feedbackTracker.storeFeedBack(Boolean.TRUE);
                return result;
            } catch(Exception e) {
                feedbackTracker.storeFeedBack(Boolean.FALSE);
                throw e;
            }
        });

    }

    private boolean isTimeElapsed(long lastModifiedAt) {
        return System.currentTimeMillis() - lastModifiedAt > timeout;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        try {
            if (lock.tryLock(timeout , TimeUnit.MILLISECONDS)) {
                try {
                    while(!feedbackTracker.canAcquirePipeline()) condition.await(timeout, TimeUnit.MILLISECONDS); // WAIT FOR SIGNAL
                } finally {
                    lock.unlock();
                }
            }
        } catch(InterruptedException e) {
            t.interrupt();
        }
    }

}
