package com.eshop.app.consumer.task.manager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Remember. channel status record in a sliding window.
 *
 */
public class FeedbackTracker implements  IFeedbackTracker {

    private final Deque<Boolean> channelRecords;
    private final ReentrantLock lock = new ReentrantLock();
    private final int capacity;
    private final int tGreen = 75;
    private static final int timeout = 100;

    public FeedbackTracker (int capacity) {
        channelRecords = new ArrayDeque<>(capacity + 1);
        this.capacity = capacity;
        reset();
    }

    @Override
    public boolean canAcquirePipeline() {
        return getChannelStatus() != ChannelStatus.RED;
    }

    @Override
    public ChannelStatus getChannelStatus() {
        long tSuccessCount = calculateCount(Boolean.TRUE);
        long tfailureCount = calculateCount(Boolean.FALSE);
        long totalInPercentage = tSuccessCount * 100 / (tSuccessCount + tfailureCount);
        return getChannelStatus(totalInPercentage);
    }

    private ChannelStatus getChannelStatus(long totalInPercentage) {
        if (totalInPercentage >= tGreen) return  ChannelStatus.GREEN;
        return ChannelStatus.RED;
    }

    private long calculateCount(Boolean status) {
        return this.channelRecords.stream().filter(x -> x == status).count();
    }

    @Override
    public void reset() {
        IntStream.rangeClosed(1, capacity).forEach(x -> this.channelRecords.addLast(Boolean.TRUE));
    }

    @Override
    public void storeFeedBack(Boolean status) {
        try {
            if(lock.tryLock(timeout , TimeUnit.MILLISECONDS)) {
                try {
                    this.channelRecords.addLast(status);
                    if (this.channelRecords.size() > capacity ) {
                        this.channelRecords.removeFirst();
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
