package org.garden.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.garden.com.dto.CreateUserDto;
import org.garden.com.dto.EditUserDto;
import org.garden.com.entity.User;
import org.garden.com.service.UserServiceImpl;
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
    private UserServiceImpl userService;

    @Test
    public void testCreateUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("Test User", "Test Email",
                "11223344", "7775533");

        User createdUser = new User();
        when(userService.createUser(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createUserDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Определяем поведение мок-сервиса
        List<User> users = new ArrayList<>();
        when(userService.getAllUsers())
                .thenReturn(users);

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUserById() throws Exception {
        EditUserDto editUserDto = new EditUserDto("New Name", "New number");

        User updatedUser = new User();
        when(userService.editUser(anyLong(), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/v1/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(editUserDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
        when(userService.findUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/v1/users/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserById() throws Exception {
        ResponseEntity<Void> responseEntity = ResponseEntity.ok().build();
        when(userService.deleteUser(anyLong())).thenReturn(responseEntity);

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
