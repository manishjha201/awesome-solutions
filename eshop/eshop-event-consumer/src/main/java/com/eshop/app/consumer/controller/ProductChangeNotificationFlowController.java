package com.eshop.app.consumer.controller;

import com.eshop.app.common.models.EShoppingChangeEvent;
import com.eshop.app.common.models.NotificationData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class ProductChangeNotificationFlowController {
    private Map<String, List<NotificationData>> payloads;
    private EShoppingChangeEvent event;
    private List<NotificationData> preparedNotifications;

}
