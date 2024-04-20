package com.eshop.app.common.models;

import com.eshop.app.common.constants.ChangeType;
import com.eshop.app.common.constants.FormType;
import com.eshop.app.common.models.kafka.Tenant;
import com.eshop.app.common.models.kafka.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = ProductChangeMetaData.ProductChangeMetaDataBuilder.class)
@JsonSerialize
@ToString
public class ProductChangeMetaData implements Serializable {
    private String changeType;
    private String formType;
    private User user;
    private Tenant tenant;
    private Map<String, String> otherInfo;
    private Long timeAt;
    //private TimeZone timeZoneAt;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductChangeMetaDataBuilder {}
}
