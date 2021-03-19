package com.strichka.repository;

import com.strichka.entity.User;

import java.util.List;

public interface UserRepository {
    void save(User user);

    User findById(long id);

    List<User> findAll();

    void remove(User user);

    User getUserByUsername(String username);
}
