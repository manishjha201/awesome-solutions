package com.eshop.app.consumer.task.manager;

public interface IFeedbackTracker {
    boolean canAcquirePipeline();
    ChannelStatus getChannelStatus();
    void reset();
    void storeFeedBack(Boolean status);
}
