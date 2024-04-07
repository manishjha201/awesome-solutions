package com.eshop.app.controllers;

import com.eshop.app.common.entities.nosql.Tenant;
import com.eshop.app.services.catalogue.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TenantController {

    @Autowired
    private ITenantService tenantService;

    @GetMapping("/tenants")
    public List<Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }

    @GetMapping("/tenants/{tenantId}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable UUID tenantId) {
        Optional<Tenant> tenant = tenantService.getTenantById(tenantId);
        return tenant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/tenants")
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        Tenant createdTenant = tenantService.createTenant(tenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTenant);
    }

    @PutMapping("/tenants/{tenantId}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable UUID tenantId, @RequestBody Tenant updatedTenant) {
        Tenant tenant = tenantService.updateTenant(tenantId, updatedTenant);
        return ResponseEntity.ok(tenant);
    }

    @DeleteMapping("/tenants/{tenantId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable UUID tenantId) {
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }
}
