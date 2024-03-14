package org.garden.com.service;

import org.garden.com.entity.User;
import java.util.List;

public interface UserService {

    User create(User user);
    List<User> getAllUsers();
}
