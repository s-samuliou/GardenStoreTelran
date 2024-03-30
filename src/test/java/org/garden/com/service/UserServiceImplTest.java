package org.garden.com.service;

import org.garden.com.entity.User;
import org.garden.com.enums.Role;
import org.garden.com.exceptions.UserNotFoundException;
import org.garden.com.repository.UserJpaRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserJpaRepository repository;

    public UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void create_ValidUser_ReturnsCreatedUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("Test Email");
        user.setPassword("qwerty");
        user.setPhoneNumber("1115555");

        when(repository.save(user)).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals(user, createdUser);
        verify(repository, times(1)).save(user);
    }

    @Test
    public void create_InvalidUser_ThrowsException() {
        User invalidUser = new User();
        invalidUser.setName(null);
        invalidUser.setEmail("www.google.com");
        invalidUser.setPassword("123");
        invalidUser.setPhoneNumber("1117744");

        when(repository.save(any(User.class))).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> userService.create(invalidUser));
    }

    @Test
    public void testGetAll() {
        User user1 = new User();
        User user2 = new User();
        user1.setName("Name User1");
        user1.setEmail("abc@ukr.net");
        user1.setPassword("qwerty888");
        user1.setPhoneNumber("6663333");
        user2.setName("Name User2");
        user2.setEmail("abc@gmail.com");
        user2.setPassword("zxcvbn123");
        user2.setPhoneNumber("4440000");
        List<User> userList = Arrays.asList(user1, user2);
        when(repository.findAll()).thenReturn(userList);

        List<User> result = userService.getAll();

        assertEquals(userList, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void editUser_ExistingUser_ReturnsUpdatedUser() {
        long id = 1L;
        User existingUser = new User();
        User updatedUser = new User();
        when(repository.findById(id)).thenReturn(Optional.of(existingUser));
        when(repository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.edit(id, existingUser);

        assertNotNull(result);
        assertEquals(updatedUser, result);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(existingUser);
    }

    @Test
    public void getById() {
        User user = new User(1L, "User Name", "user@example.com", "qwerty55", "5558822", Role.CUSTOMER);
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getById(1L);

        assertEquals(user, foundUser);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void delete_ExistingUser_ReturnsOkResponse() {

        User user = new User(1L, "User Name", "user@example.com", "qwerty777", "3332222", Role.ADMIN);
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<Void> response = userService.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void delete_NonExistingUser_ThrowsUserNotFoundException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(1L));

        verify(repository, times(1)).findById(1L);
        verify(repository, never()).deleteById(1L);
    }
}