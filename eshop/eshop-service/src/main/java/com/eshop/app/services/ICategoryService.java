package com.eshop.app.services;

import com.eshop.app.common.entities.rdbms.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long categoryId);
    Category createCategory(Category category);
    Category updateCategory(Long categoryId, Category updatedCategory);
    void deleteCategory(Long categoryId);
}
