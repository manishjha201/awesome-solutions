package com.eshop.app.common.models;

import com.eshop.app.common.constants.NotificationEventType;
import com.eshop.app.common.entities.rdbms.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.TimeZone;

@Builder
@Data
public class NotificationData implements Serializable {
    private static final long serialVersionUID = -2515195369774105913L;

    private String UUID;
    private String notificationTo;
    private String notificationBcc;
    private String notificationCc;
    private String notificationType;
    private NotificationEventType notificationEventChannel;
    private String templateData;
    private String subjectLine;
    private String retryCount;
    private String sourceId;
    private String status;
    private Long createdAt;
    private TimeZone timeZone;
    private User createdBy;
}
