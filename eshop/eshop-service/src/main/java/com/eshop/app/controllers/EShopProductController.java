package com.eshop.app.controllers;

import com.eshop.app.common.entities.nosql.Product;
import com.eshop.app.exception.services.catalogue.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.fge.jsonpatch.JsonPatch;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class EShopProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID productId) {
        Product product = productService.getProduct(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID productId, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(productId, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Product> patchProduct(@PathVariable UUID productId, @RequestBody JsonPatch patch) {
        Product patchedProduct = productService.patchProduct(productId, patch);
        return ResponseEntity.ok(patchedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
