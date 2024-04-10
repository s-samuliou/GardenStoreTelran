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
    private CategoryServiceImpl service;

    @Mock
    private CategoryJpaRepository repository;

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

        when(repository.save(category)).thenReturn(category);

        Category createdCategory = service.create(category);

        assertNotNull(createdCategory);
        assertEquals(category, createdCategory);
        verify(repository, times(1)).save(category);
    }

    @Test
    public void testEditCategory() {
        long categoryId = 1L;
        Category existingCategory = new Category();
        Category updatedCategory = new Category();
        when(repository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(repository.save(existingCategory)).thenReturn(updatedCategory);

        Category result = service.edit(categoryId, existingCategory);

        assertNotNull(result);
        assertEquals(updatedCategory, result);
        verify(repository, times(1)).findById(categoryId);
        verify(repository, times(1)).save(existingCategory);
    }

    @Test
    public void testEditCategory_NotFoundException() {
        Category nonExistingCategory = new Category(1L, "Non Existing Category");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            service.edit(1L, nonExistingCategory);
        });
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(Category.class));
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

        when(repository.findAll()).thenReturn(categories);

        List<Category> retrievedCategories = service.getAll();

        assertEquals(categories.size(), retrievedCategories.size());
        assertTrue(retrievedCategories.containsAll(categories));
        for (int i = 0; i < categories.size(); i++) {
            assertEquals(categories.get(i), retrievedCategories.get(i));
        }
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testDeleteById_SuccessfulDeletion() {

        long categoryId = 123L;

        when(repository.existsById(categoryId)).thenReturn(true);

        service.deleteById(categoryId);

        verify(repository, times(1)).existsById(categoryId);
        verify(repository, times(1)).deleteById(categoryId);
    }

    @Test
    public void testDeleteCategory_CategoryNotFoundException() {
        long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            service.deleteById(id);
        });
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(id);
    }


}
