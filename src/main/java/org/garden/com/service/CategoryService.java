package org.garden.com.service;

import org.garden.com.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    List<Category> getAll();

    Category edit(long id, Category category);

    void deleteById(long id);
}
