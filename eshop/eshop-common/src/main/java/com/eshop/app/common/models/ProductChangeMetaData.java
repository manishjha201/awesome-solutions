package com.eshop.app.common.models;

import com.eshop.app.common.constants.ChangeType;
import com.eshop.app.common.constants.FormType;
import com.eshop.app.common.entities.nosql.cassandra.Tenant;
import com.eshop.app.common.entities.nosql.cassandra.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.TimeZone;

@Data
@Builder
public class ProductChangeMetaData implements Serializable {
    private ChangeType changeType;
    private FormType formType;
    private User user;
    private Tenant tenant;
    private Map<String, String> otherInfo;
    private Long timeAt;
    private TimeZone timeZoneAt;
}
