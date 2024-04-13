package com.eshop.app.client;

import com.eshop.app.models.resp.TenantResp;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ITenantClient {
    TenantResp getTenant(Long tenantId, String loginId, String esToken) throws JsonProcessingException;
}
