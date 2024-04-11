package com.eshop.app.common.models.kafka;

import com.eshop.app.common.constants.Status;
import com.eshop.app.common.constants.TenantType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = Tenant.TenantBuilder.class)
@JsonSerialize
@ToString
public class Tenant implements Serializable {
    private static final long serialVersionUID = -2626715953970054402L;
    private Long id;
    private String name;
    private String type;
    private String createdBy;
    private String lastUpdatedBy;
    private Status status;
    private int version;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TenantBuilder {}
}
