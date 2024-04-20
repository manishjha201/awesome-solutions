package com.eshop.app.controllers.internal;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.entities.rdbms.Tenant;
import com.eshop.app.models.resp.GenericResponseBody;
import com.eshop.app.models.resp.ResultInfo;
import com.eshop.app.models.resp.TenantResp;
import com.eshop.app.services.ITenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/internal/data/v1/catalog/tenants")
public class TenantController {

    @Autowired
    private ITenantService tenantService;

    @GetMapping("")
    public List<Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }

    @GetMapping("{tenantId}")
    public ResponseEntity<GenericResponseBody<TenantResp>> getTenantById(@PathVariable Long tenantId) {
        log.info("Request received for API={} with values : {}", "GET_TENANT_API", tenantId);
        Optional<TenantResp> tenant = tenantService.getTenantById(tenantId);
        GenericResponseBody<TenantResp> body = new GenericResponseBody<>();
        body.setResponse(tenant.get());
        body.setResultInfo(ResultInfo.builder().resultCode(EShopResultCode.SUCCESS).build());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        Tenant createdTenant = tenantService.createTenant(tenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTenant);
    }

    @PutMapping("{tenantId}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long tenantId, @RequestBody Tenant updatedTenant) {
        Tenant tenant = tenantService.updateTenant(tenantId, updatedTenant);
        return ResponseEntity.ok(tenant);
    }

    @DeleteMapping("{tenantId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long tenantId) {
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }
}
