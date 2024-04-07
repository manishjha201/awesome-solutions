package com.eshop.app.consumer.services;

import com.eshop.app.common.models.NotificationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationService implements INotificationService {

    @Override
    public boolean sendNotification(NotificationData notification) {
        //TODO : TBD
        return true;
    }
}
