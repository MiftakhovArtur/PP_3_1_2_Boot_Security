package ru.kata.spring.boot_security.demo.repository;


import ru.kata.spring.boot_security.demo.models.*;

import java.util.*;

public interface UserRepository {
    void save(User user);

    void update(User user);

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    void delete(User user);
}
