package com.eshop.app.services;

import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.common.repositories.rdbms.master.ProductRepository;
import com.eshop.app.exception.ResourceNotFoundException;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements IProductService {


    @Autowired
    @PersistenceContext(unitName = "master")
    private EntityManager masterEntityManager;

    @Autowired
    @PersistenceContext(unitName = "slave")
    private EntityManager slaveEntityManager;

    @Transactional(value = "masterTransactionManager", readOnly = false)
    @Override
    public Product createProduct(Product product) {
        slaveEntityManager.persist(product);
        return product;
    }

    @Override
    public Product getProduct(UUID productId) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Transactional(value = "masterTransactionManager", readOnly = false)
    @Override
    public Product updateProduct(UUID productId, Product product) {
        return null;
    }

    @Transactional(value = "masterTransactionManager", readOnly = false)
    @Override
    public Product patchProduct(UUID productId, JsonPatch patch) {
        return null;
    }

    @Transactional(value = "masterTransactionManager", readOnly = false)
    @Override
    public void deleteProduct(UUID productId) throws ResourceNotFoundException {

    }
}
