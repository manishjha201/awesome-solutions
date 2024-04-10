package com.eshop.app.services.catalog;

import com.eshop.app.common.entities.nosql.cassandra.Tenant;
import com.eshop.app.common.repositories.nosql.cassandra.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService implements ITenantService {

    @Autowired
    private TenantRepository tenantRepository;


    @Override
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    @Override
    public Optional<Tenant> getTenantById(UUID tenantId) {
        return tenantRepository.findById(tenantId);
    }

    @Override
    public Tenant createTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant updateTenant(UUID tenantId, Tenant updatedTenant) {
        //TODO
        updatedTenant.setTenantId(tenantId);
        return tenantRepository.save(updatedTenant);
    }

    @Override
    public void deleteTenant(UUID tenantId) {
        tenantRepository.deleteById(tenantId);
    }
}
