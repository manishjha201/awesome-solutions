package com.eshop.app.services;

import com.eshop.app.common.entities.rdbms.Product;
import com.eshop.app.exception.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;
import com.github.fge.jsonpatch.JsonPatch;

public interface IProductService {
    Product createProduct(Product product);
    Product getProduct(UUID productId) throws ResourceNotFoundException;
    List<Product> getAllProducts();
    Product updateProduct(UUID productId, Product product);
    Product patchProduct(UUID productId, JsonPatch patch);
    void deleteProduct(UUID productId) throws ResourceNotFoundException;
}
