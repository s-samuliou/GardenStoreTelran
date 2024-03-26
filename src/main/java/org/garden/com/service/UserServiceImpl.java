package org.garden.com.service;

import org.garden.com.entity.Cart;
import org.garden.com.entity.User;
import org.garden.com.exceptions.UserNotFoundException;
import org.garden.com.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepository repository;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);



    @Override
    public User createUser(User user) {
        log.info("Creating user: {}", user);
 //       user.setCart(new Cart());
        return repository.save(user);
    }

    @Override
    public List<User> getAllUsers() {

        log.info("Fetching users");
        return repository.findAll();
    }

    @Override
    public User editUser(long id, User user) {
        log.info("Editing user with ID {}: {}", id, user);
        User existingUser = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        existingUser.setName(user.getName());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        User updatedUser = repository.save(existingUser);
        log.info("User updated: {}", updatedUser);
        return updatedUser;
    }

    @Override
    public User findUserById(long id) {
        log.info("Fetching user with ID: {}", id);
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        log.info("Found user: {}", user);
        return user;
    }

    @Override
    public ResponseEntity<Void> deleteUser(long id) {
        log.info("Deleting user with ID: {}", id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            log.info("User with ID {} deleted", id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            log.warn("User not deleted: {}", id);
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
}
