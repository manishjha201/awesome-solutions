package com.eshop.app.models.resp;

import com.eshop.app.common.models.kafka.Tenant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = TenantResp.TenantRespBuilder.class)
public class TenantResp {
    private Tenant tenant;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TenantRespBuilder {
        private Tenant tenant;

        public TenantRespBuilder tenant(Tenant tenant) {
            this.tenant = tenant;
            return this;
        }

        public TenantResp build() {
            return new TenantResp(tenant);
        }
    }
}
