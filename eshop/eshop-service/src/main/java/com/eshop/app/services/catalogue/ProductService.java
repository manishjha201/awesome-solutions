package com.eshop.app.services.catalogue;

import com.eshop.app.common.constants.EShopResultCode;
import com.eshop.app.common.entities.nosql.Product;
import com.eshop.app.common.repositories.nosql.cassandra.ProductRepository;
import com.eshop.app.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        //TODO : Perform any business logic/validation before saving the product
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(EShopResultCode.NOT_FOUND, "Product not found with id: " + productId));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll(); //TODO : not for disabled items
    }

    @Override
    public Product updateProduct(UUID productId, Product product) {
        Product existingProduct = getProduct(productId);
        //TODO : Perform any business logic/validation before updating the product
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCatalogId(product.getCatalogId());
        existingProduct.setCategoryId(product.getCategoryId());
        existingProduct.setTenantId(product.getTenantId());
        existingProduct.setImageUrl(product.getImageUrl());
        //TODO :  Update other fields as needed
        return productRepository.save(existingProduct);
    }

    @Override
    public Product patchProduct(UUID productId, JsonPatch patch) {
        Product existingProduct = getProduct(productId);

        Product newProduct = new Product();
        //TODO : Set properties of the product
        newProduct.setName("New Product Name");
        newProduct.setPrice(BigDecimal.valueOf(100.00));

        // Convert the Product object to a JSON Node
        JsonNode productNode = newProduct.toJsonNode();

        // Create a JSON Patch to update the product
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode patchNode = objectMapper.createObjectNode();
        patchNode.put("/name", "Updated Product Name"); // Specify the field to update and its new value

        // Convert the patchNode to a JsonPatch object
        JsonPatch patch1 = null;

        // Apply the patch to the productNode
        try {
            patch1 = JsonPatch.fromJson(patchNode);
            JsonNode patchedNode = patch1.apply(productNode);
            // Convert the patchedNode back to a Product object if needed
            Product patchedProduct = objectMapper.treeToValue(patchedNode, Product.class);
            // Now the patchedProduct contains the updated properties
            return productRepository.save(patchedProduct);
        } catch (JsonPatchException e) {
            // Handle JsonPatchException
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteProduct(UUID productId) {
        Product existingProduct = getProduct(productId);
        productRepository.delete(existingProduct);
    }
}
