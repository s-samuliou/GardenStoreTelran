package org.garden.com.service;

import org.garden.com.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User editUser(long id, User user);

    User findUserById(long id);

    ResponseEntity<Void> deleteUser(long id);
}
