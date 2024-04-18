package com.eshop.app.consumer.handlers;

import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.consumer.task.manager.*;
import com.eshop.app.consumer.exceptions.BusinessException;
import com.eshop.app.consumer.filters.IEsShoppingFeedFilter;
import com.eshop.app.consumer.models.ProcessedFeedOutput;
import com.eshop.app.consumer.parser.IEshopEventInfoParser;
import com.eshop.app.consumer.services.IEsDataIngestionService;
import com.eshop.app.consumer.services.INotificationService;
import com.sun.xml.bind.v2.runtime.RuntimeUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.eshop.app.consumer.factories.ChangeEventProcessorFactory;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@Builder
@Data
public final class EShoppingEventHandler implements IEShoppingEventHandler {

    private final Object message;
    private final IEshopEventInfoParser eshopEventInfoParser;
    private final IEsShoppingFeedFilter esShoppingFeedFilter;
    private final IEsDataIngestionService esDataIngestionService;
    private final INotificationService notificationService;

    public EShoppingEventHandler(final Object message, final IEshopEventInfoParser eshopEventInfoParser,final IEsShoppingFeedFilter esShoppingFeedFilter, final IEsDataIngestionService esDataIngestionService, final INotificationService notificationService) {
        this.message = message;
        this.eshopEventInfoParser = eshopEventInfoParser;
        this.esShoppingFeedFilter = esShoppingFeedFilter;
        this.esDataIngestionService = esDataIngestionService;
        this.notificationService = notificationService;
    }

    @Override
    public ProcessedFeedOutput process() throws BusinessException {
        long startTime = System.currentTimeMillis();
        ProcessedFeedOutput output = ProcessedFeedOutput.builder()
                .isSuccess(true)
                .responseMsg("processing||")
                .responseCode("000")
                .build();
        try {
            EShoppingChangeEvent changeEvent = eshopEventInfoParser.parse(message);

            //TODO : INGEST IN ES

            Boolean isInLowInventory = new ChangeEventProcessorFactory().get(changeEvent.getProductChangeMetaData().getChangeType()).process(changeEvent);
            if (isInLowInventory) {
                EShoppingLowVolumeProductTask task = EShoppingLowVolumeProductTask.builder().event(changeEvent).notificationService(this.notificationService).build();
                CompletableFuture<ProcessedFeedOutput> future = RecordManager.submitPayloadRequest(task);
                future.thenAccept(result -> {
                    log.info("Submitted  task resp : {}" + result);
                }).exceptionally(exception -> {
                    log.error("Exception occurred: {} ", exception);
                    return null;
                });
            }

            if (isInLowInventory) { //TODO : move to different group id listener , use different threadPoolExecutor
                EShoppingESUpdateTask task = EShoppingESUpdateTask.builder().event(changeEvent).dataIngestionService(this.esDataIngestionService).build();
                CompletableFuture<ProcessedFeedOutput> future = RecordManager.submitPayloadRequest(task);
                future.thenAccept(result -> {
                    log.info("Submitted  task resp : {}" + result);
                }).exceptionally(exception -> {
                    log.error("Exception occurred: {} ", exception);
                    return null;
                });
            }
        } catch(BusinessException interruptedException) {
            log.error("Error processing the task {}", message, interruptedException);
            output.setIsSuccess(Boolean.FALSE);
            output.setResponseMsg(output.getResponseMsg().concat(interruptedException.getMessage()));
            Thread.currentThread().interrupt();
        } catch(Exception e) {
            log.error("Error processing the task : {}", message, e);
            output.setIsSuccess(Boolean.FALSE);
            output.setResponseMsg(output.getResponseMsg().concat(e.getMessage()));
        } finally {
            long timeTaken = System.currentTimeMillis() - startTime;
            log.info("Task processing took {} ms", timeTaken);
            output.setTimeTakenToProcess(timeTaken);
        }
        return output;
    }
}
