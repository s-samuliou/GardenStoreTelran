package org.garden.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.garden.com.converter.UserMapper;
import org.garden.com.dto.CreateUserDto;
import org.garden.com.dto.EditUserDto;
import org.garden.com.entity.User;
import org.garden.com.service.UserService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper userMapper;

    @Test
    public void testCreate() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("Test User", "Test Email",
                "11223344", "7775533");

        User createdUser = new User();
        when(service.create(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createUserDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAll() throws Exception {

        List<User> users = new ArrayList<>();
        when(service.getAll())
                .thenReturn(users);

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditById() throws Exception {
        EditUserDto editUserDto = new EditUserDto("New Name", "New number");

        User updatedUser = new User();
        when(service.edit(anyLong(), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/v1/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(editUserDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        User user = new User();
        when(service.getById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/v1/users/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteById() throws Exception {
        ResponseEntity<Void> responseEntity = ResponseEntity.ok().build();
        when(service.delete(anyLong())).thenReturn(responseEntity);

        mockMvc.perform(delete("/v1/users/{id}", 1))
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