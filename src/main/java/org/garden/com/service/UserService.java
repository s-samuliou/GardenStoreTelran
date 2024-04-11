package org.garden.com.service;

import org.garden.com.entity.User;

import java.util.List;

public interface UserService {

    User create(User user);

    List<User> getAll();

    User edit(long id, User user);

    User getById(long id);

    void delete(long id);

    User getByLogin(String login);
}
