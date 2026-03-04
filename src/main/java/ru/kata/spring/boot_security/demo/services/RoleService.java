package ru.kata.spring.boot_security.demo.services;

import java.util.List;
import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleService {
    void save(Role role);

    Role findByName(String name);

    List<Role> findAll();

    void delete(Role role);
}
