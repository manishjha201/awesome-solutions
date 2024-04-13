package com.eshop.app.services;


import com.eshop.app.common.entities.rdbms.Tenant;
import com.eshop.app.models.resp.TenantResp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITenantService {

    List<Tenant> getAllTenants();
    Optional<TenantResp> getTenantById(Long tenantId);
    Tenant createTenant(Tenant tenant);
    Tenant updateTenant(Long tenantId, Tenant updatedTenant);
    void deleteTenant(Long tenantId);
}
