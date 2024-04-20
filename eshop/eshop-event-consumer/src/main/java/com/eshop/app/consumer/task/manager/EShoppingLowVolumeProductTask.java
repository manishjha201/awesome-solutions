package com.eshop.app.consumer.task.manager;

import com.eshop.app.common.constants.ProductChangeEventNotificationType;
import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.common.models.NotificationData;
import com.eshop.app.consumer.controller.ProductChangeEventController;
import com.eshop.app.consumer.controller.ProductChangeNotificationFlowController;
import com.eshop.app.consumer.factories.NotificationEnhancerFactory;
import com.eshop.app.consumer.models.ProcessedFeedOutput;
import com.eshop.app.consumer.services.INotificationService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public final class EShoppingLowVolumeProductTask implements Callable<ProcessedFeedOutput> {

    private final EShoppingChangeEvent event;
    private final INotificationService notificationService;

    @Builder
    public EShoppingLowVolumeProductTask(final EShoppingChangeEvent event, final INotificationService notificationService) {
        this.event = event;
        this.notificationService =notificationService;
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
            List<NotificationData> pendingNotifications = new ArrayList<>();
            ProductChangeNotificationFlowController controller = ProductChangeNotificationFlowController.builder().event(event).preparedNotifications(pendingNotifications).build();
            for(ProductChangeEventNotificationType type : ProductChangeEventNotificationType.values()) {
                controller = new NotificationEnhancerFactory().get(type).enhance(controller); // prepare all notifications required
                pendingNotifications.addAll(controller.getPayloads().get(type.getLabel()));
            }
            log.info(" Prepared Product change pendingNotifications for event : {} of size : {}", pendingNotifications, pendingNotifications.size());
            for (NotificationData nextNotification : pendingNotifications) {
                boolean status = notificationService.sendNotification(nextNotification);
                if(status) successCount++;
                else failureCount++;
                isSuccess = isSuccess && status;
            }
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
