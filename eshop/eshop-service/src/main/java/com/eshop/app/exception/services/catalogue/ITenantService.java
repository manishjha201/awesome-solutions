package com.eshop.app.exception.services.catalogue;

import com.eshop.app.common.entities.nosql.Tenant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITenantService {

    public List<Tenant> getAllTenants();
    Optional<Tenant> getTenantById(UUID tenantId);
    Tenant createTenant(Tenant tenant);
    Tenant updateTenant(UUID tenantId, Tenant updatedTenant);
    void deleteTenant(UUID tenantId);
}
