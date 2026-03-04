package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.*;

import java.util.*;

public interface UserService {
    void save(User user);

    void updateUser(User user);

    User findByUsername(String username);

    User findById(Long id);

    List<User> findAll();

    void delete(User user);
}
