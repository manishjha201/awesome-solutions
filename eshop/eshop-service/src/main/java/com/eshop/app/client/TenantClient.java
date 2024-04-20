package com.eshop.app.client;

import com.eshop.app.models.resp.ProductResp;
import com.eshop.app.models.resp.TenantResp;
import com.eshop.app.services.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class TenantClient implements ITenantClient {

    @Autowired
    @Qualifier("tenantClientService")
    private DataService<TenantResp,  TenantResp> tenantClientService;

    @Override
    public TenantResp getTenant(Long tenantId, String loginId, String esToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("estoken", esToken);
        headers.add("loginId", loginId);
        //in seconds
        long ttl = 60 * 60;
        TenantResp resp = tenantClientService.getData("eshop:catalog:tenant", tenantId.toString(), TenantResp.class, Collections.emptyMap(), headers, ttl);
        log.info("data received : {} ", resp);
        return resp;
    }
}
