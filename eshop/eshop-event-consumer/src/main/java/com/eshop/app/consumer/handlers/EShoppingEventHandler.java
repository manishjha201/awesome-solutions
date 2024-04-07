package com.eshop.app.consumer.handlers;

import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.consumer.exceptions.BusinessException;
import com.eshop.app.consumer.filters.IEsShoppingFeedFilter;
import com.eshop.app.consumer.models.ProcessedFeedOutput;
import com.eshop.app.consumer.parser.IEshopEventInfoParser;
import com.eshop.app.consumer.services.IEsDataIngestionService;
import com.eshop.app.consumer.services.INotificationService;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Data
public final class EShoppingEventHandler implements IEShoppingEventHandler {

    private final String message;
    private final IEshopEventInfoParser eshopEventInfoParser;
    private final IEsShoppingFeedFilter esShoppingFeedFilter;
    private final IEsDataIngestionService esDataIngestionService;
    private final INotificationService notificationService;

    public EShoppingEventHandler(final String message, final IEshopEventInfoParser eshopEventInfoParser,final IEsShoppingFeedFilter esShoppingFeedFilter, final IEsDataIngestionService esDataIngestionService, final INotificationService notificationService) {
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
           if (true) {
               //TODO : pending
               Thread.sleep(1000);
           }
        } catch(InterruptedException interruptedException) {
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
