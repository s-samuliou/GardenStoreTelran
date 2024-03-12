package org.garden.com.service;

import org.garden.com.entity.UserEntity;
import java.util.List;

public interface UserService {

    UserEntity create(UserEntity userEntity);

 //   UserEntity getByName(String name);

   List<UserEntity> getAll();
}
