package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.models.*;

import java.util.*;

public interface RoleRepository {
    void save(Role role);

    Role findByName(String name);

    List<Role> findAll();

    void delete(Role role);
}
