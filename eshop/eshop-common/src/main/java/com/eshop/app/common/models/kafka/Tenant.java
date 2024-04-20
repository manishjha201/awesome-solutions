package com.eshop.app.common.models.kafka;

import com.eshop.app.common.constants.Status;
import com.eshop.app.common.constants.TenantType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tenant implements Serializable {
    private static final long serialVersionUID = -2626715953970055402L;
    private Long id;
    private String name;
    private String type;
    private String createdBy;
    private String lastUpdatedBy;
    private Status status;
    private int version;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TenantBuilder {
        private Long id;
        private String name;
        private String type;
        private String createdBy;
        private String lastUpdatedBy;
        private Status status;
        private int version;

        public TenantBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TenantBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TenantBuilder type(String type) {
            this.type = type;
            return this;
        }

        public TenantBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public TenantBuilder lastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
            return this;
        }

        public TenantBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public TenantBuilder version(int version) {
            this.version = version;
            return this;
        }

        public Tenant build() {
            return new Tenant(id, name, type, createdBy, lastUpdatedBy, status, version);
        }
    }
}
