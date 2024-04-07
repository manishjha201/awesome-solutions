package com.eshop.app.consumer.services;

import com.eshop.app.common.models.NotificationData;

public interface INotificationService {
    boolean sendNotification(NotificationData notification);
}
