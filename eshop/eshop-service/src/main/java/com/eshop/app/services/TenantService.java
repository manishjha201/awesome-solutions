package com.eshop.app.services;

import com.eshop.app.common.constants.Status;
import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.common.entities.rdbms.Tenant;
import com.eshop.app.mapper.resp.ResponseMapper;
import com.eshop.app.models.resp.TenantResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    @Transactional(value = "eShopSlaveTransactionManager", readOnly = true)
    @Override
    public Optional<TenantResp> getTenantById(Long tenantId) {
        CriteriaBuilder cb = slaveEntityManager.getCriteriaBuilder();
        CriteriaQuery<Tenant> cq = cb.createQuery(Tenant.class);
        Root<Tenant> tenant = cq.from(Tenant.class);
        List<Long> ids = List.of(tenantId);
        cq.select(tenant)
                .where(cb.notEqual(tenant.get("status"), Status.DELETED),
                        tenant.get("id").in(ids));
        List<Tenant> tenants = slaveEntityManager.createQuery(cq).getResultList();
        return ResponseMapper.mapGetTenantDetailResponse(tenants);
    }

    @Override
    public Tenant createTenant(Tenant tenant) {
        return null;
    } //TODO : pending

    @Override
    public Tenant updateTenant(Long tenantId, Tenant updatedTenant) {
        return null;
    }  //TODO : pending

    @Override
    public void deleteTenant(Long tenantId) {  //TODO : pending

    }
}
