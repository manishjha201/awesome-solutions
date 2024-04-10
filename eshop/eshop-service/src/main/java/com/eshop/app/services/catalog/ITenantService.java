package com.eshop.app.services.catalog;

import com.eshop.app.common.entities.nosql.cassandra.Tenant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITenantService {

    List<Tenant> getAllTenants();
    Optional<Tenant> getTenantById(UUID tenantId);
    Tenant createTenant(Tenant tenant);
    Tenant updateTenant(UUID tenantId, Tenant updatedTenant);
    void deleteTenant(UUID tenantId);
}
