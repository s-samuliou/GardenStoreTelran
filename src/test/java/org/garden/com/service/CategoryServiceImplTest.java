package org.garden.com.service;

import jakarta.validation.Validator;
import org.garden.com.entity.Category;
import org.garden.com.exceptions.CategoryNotFoundException;
import org.garden.com.repository.CategoryJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryJpaRepository categoryRepository;

    @Mock
    private Validator validator;

    public CategoryServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Shovels");

        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = categoryService.create(category);

        assertNotNull(createdCategory);
        assertEquals(category, createdCategory);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testEditCategory() {
        long categoryId = 1L;
        Category existingCategory = new Category();
        Category updatedCategory = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);

        Category result = categoryService.edit(categoryId, existingCategory);

        assertNotNull(result);
        assertEquals(updatedCategory, result);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    public void testEditCategory_NotFoundException() {
        Category nonExistingCategory = new Category(1L, "Non Existing Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.edit(1L, nonExistingCategory);
        });
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setId(1);
            category.setName("New one");

            categories.add(category);
        }

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> retrievedCategories = categoryService.getAll();

        assertEquals(categories.size(), retrievedCategories.size());
        assertTrue(retrievedCategories.containsAll(categories));
        for (int i = 0; i < categories.size(); i++) {
            assertEquals(categories.get(i), retrievedCategories.get(i));
        }
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteCategoryById() {
        long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> result = categoryService.deleteById(id);

        assertNotNull(result);
        assertEquals(ResponseEntity.status(HttpStatus.OK).build(), result);
        verify(categoryRepository, times(1)).existsById(id);
        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteCategory_CategoryNotFoundException() {
        long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteById(id);
        });
        verify(categoryRepository, times(1)).existsById(id);
        verify(categoryRepository, never()).deleteById(id);
    }


}
