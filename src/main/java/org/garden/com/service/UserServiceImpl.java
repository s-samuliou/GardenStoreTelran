package org.garden.com.service;

import org.garden.com.entity.User;
import org.garden.com.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepository repository;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User editUser(long id, User user) {
        User existingUser = repository.findById(id).orElseThrow();

        existingUser.setName(user.getName());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        return repository.save(existingUser);
    }

    @Override
    public User findUserById(long id) {
        return repository.findById(id).orElseThrow();
    }

    public ResponseEntity<Void> deleteUser(long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
