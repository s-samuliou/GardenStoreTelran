package org.garden.com.service;

import lombok.RequiredArgsConstructor;
import org.garden.com.entity.Category;
import org.garden.com.repository.CategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryJpaRepository repository;

    @Override
    public Category create(Category category) {
        return repository.save(category);
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }
}
