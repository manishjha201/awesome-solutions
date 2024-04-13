package com.eshop.app.controllers.internal;

import com.eshop.app.common.entities.rdbms.Category;
import com.eshop.app.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/api/v1/catalog/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category updatedCategory) {
        Category category = categoryService.updateCategory(categoryId, updatedCategory);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
