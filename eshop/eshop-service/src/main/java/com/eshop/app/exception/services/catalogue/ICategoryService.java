package com.eshop.app.exception.services.catalogue;

import com.eshop.app.common.entities.nosql.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(UUID categoryId);
    Category createCategory(Category category);
    Category updateCategory(UUID categoryId, Category updatedCategory);
    void deleteCategory(UUID categoryId);
}
