package org.garden.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.garden.com.converter.CategoryMapper;
import org.garden.com.dto.EditCategoryDto;
import org.garden.com.entity.Category;
import org.garden.com.security.JwtService;
import org.garden.com.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CategoryService service;

    @MockBean
    private CategoryMapper mapper;

    @Before
    public void setup() {
        // Grant the "ADMIN" role to the user for testing purposes
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "admin", // Username
                        null, // No password needed for testing
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
                )
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateCategory() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.
                post("/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"4\"," +
                        "\"name\": \"Scissors\"}"
                );
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        when(service.getAll()).thenReturn(categories);

        mockMvc.perform(get("/v1/categories"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCategoryById() throws Exception {
        EditCategoryDto editCategoryDto = new EditCategoryDto();

        Category updatedCategory = new Category();
        when(service.edit(anyLong(), any(Category.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/v1/categories/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(editCategoryDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCategoryById() throws Exception {

        mockMvc.perform(delete("/v1/categories/{id}", 1))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(1L);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
