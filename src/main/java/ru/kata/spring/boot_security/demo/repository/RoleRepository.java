package ru.kata.spring.boot_security.demo.repository;

import java.util.List;
import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleRepository {
    void save(Role role);

    Role findByName(String name);

    List<Role> findAll();

    void delete(Role role);
}
