package org.garden.com.service;

import org.garden.com.entity.Category;
import org.garden.com.exceptions.CategoryNotFoundException;
import org.garden.com.repository.CategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryJpaRepository repository;

    @Override
    public Category createCategory(Category category) {
        return repository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public Category editCategory(long id, Category category) {
        Category newCategory = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
        newCategory.setName(category.getName());
        repository.save(newCategory);
        return newCategory;
    }

    @Override
    public Category deleteCategoryById(long id) {
        Category categoryToDelete = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
        repository.delete(categoryToDelete);
        return categoryToDelete;

    }
}
