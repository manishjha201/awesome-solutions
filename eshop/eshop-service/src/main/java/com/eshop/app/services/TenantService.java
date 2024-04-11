package com.eshop.app.services;

import com.eshop.app.common.entities.rdbms.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService implements ITenantService {

    @Autowired
    @PersistenceContext(unitName = "master")
    private EntityManager masterEntityManager;

    @Autowired
    @PersistenceContext(unitName = "slave")
    private EntityManager slaveEntityManager;


    @Override
    public List<Tenant> getAllTenants() {
        return null;
    }

    @Override
    public Optional<Tenant> getTenantById(UUID tenantId) {
        return Optional.empty();
    }

    @Override
    public Tenant createTenant(Tenant tenant) {
        return null;
    }

    @Override
    public Tenant updateTenant(UUID tenantId, Tenant updatedTenant) {
        return null;
    }

    @Override
    public void deleteTenant(UUID tenantId) {

    }
}
