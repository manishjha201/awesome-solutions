package com.eshop.app.services.catalog;

import com.eshop.app.common.entities.nosql.cassandra.Category;
import com.eshop.app.common.repositories.nosql.cassandra.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(UUID categoryId, Category updatedCategory) {
        updatedCategory.setCategoryId(categoryId);
        return categoryRepository.save(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
