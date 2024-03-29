package org.garden.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.garden.com.converter.CategoryMapper;
import org.garden.com.dto.EditCategoryDto;
import org.garden.com.entity.Category;
import org.garden.com.service.CategoryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryServiceImpl categoryService;

    @MockBean
    private CategoryMapper mapper;

    @Test
    public void testCreateCategory() throws Exception {
        Category createdCategory = new Category();
        when(categoryService.createCategory(any(Category.class))).thenReturn(createdCategory);

        mockMvc.perform(post("/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createdCategory)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/v1/categories"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCategoryById() throws Exception {
        EditCategoryDto editCategoryDto = new EditCategoryDto();

        Category updatedCategory = new Category();
        when(categoryService.editCategory(anyLong(), any(Category.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/v1/categories/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(editCategoryDto)))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteCategoryById() throws Exception {
        ResponseEntity<Void> responseEntity = ResponseEntity.ok().build();
        when(categoryService.deleteCategoryById(anyLong())).thenReturn(responseEntity);

        mockMvc.perform(delete("/v1/categories/{id}", 1))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
