package com.eshop.app.controllers;

import com.eshop.app.common.entities.nosql.Category;
import com.eshop.app.exception.services.catalogue.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID categoryId) {
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID categoryId, @RequestBody Category updatedCategory) {
        Category category = categoryService.updateCategory(categoryId, updatedCategory);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
