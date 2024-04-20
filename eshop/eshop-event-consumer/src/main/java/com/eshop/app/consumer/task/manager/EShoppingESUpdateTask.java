package com.eshop.app.consumer.task.manager;

import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.consumer.factories.ChangeEventEsUpdateFactory;
import com.eshop.app.consumer.models.ProcessedFeedOutput;
import com.eshop.app.consumer.services.IEsDataIngestionService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class EShoppingESUpdateTask implements Callable<ProcessedFeedOutput>  {

    private final EShoppingChangeEvent event;
    private final IEsDataIngestionService dataIngestionService;

    @Builder
    public EShoppingESUpdateTask(EShoppingChangeEvent event, IEsDataIngestionService dataIngestionService) {
        this.event = event;
        this.dataIngestionService = dataIngestionService;
    }

    @Override
    public ProcessedFeedOutput call() throws Exception {
        return processRecord(event);
    }

    private ProcessedFeedOutput processRecord(EShoppingChangeEvent event) {
        String errorMsg = "";
        boolean isSuccess = true;
        long timeNow = System.currentTimeMillis();
        int successCount = 0;
        int failureCount = 0;
        long timeTakenToProcess = 0;
        try {
             new ChangeEventEsUpdateFactory().get(event.getProductChangeMetaData().getChangeType()).process(this.dataIngestionService, this.event);
        } catch(Exception e) {
            log.error("Exception occurred : {}", e);
            errorMsg = e.getMessage();
        } finally {
            timeTakenToProcess = System.currentTimeMillis() - timeNow;
            log.info("count success : {} , fail : {}", successCount, failureCount);
        }
        return ProcessedFeedOutput.builder().isSuccess(isSuccess).responseMsg(errorMsg).timeTakenToProcess(timeTakenToProcess).build();
    }
}
