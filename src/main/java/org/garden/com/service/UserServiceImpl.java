package org.garden.com.service;

import lombok.RequiredArgsConstructor;
import org.garden.com.entity.UserEntity;
import org.garden.com.exceptions.UserNotFoundException;
import org.garden.com.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

 private final UserJpaRepository repository;
    @Override
    public UserEntity create(UserEntity userEntity) {
        return null;
    }

//    @Override
//    public UserEntity getByName(String name) {
//        return repository.findAllByName(name).orElseThrow(()-> new UserNotFoundException("User with name " + name + " not found"));
//    }

    @Override
    public List<UserEntity> getAll() {
        return repository.findAll();
    }
}
