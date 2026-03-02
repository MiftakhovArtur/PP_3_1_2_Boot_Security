package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role; // импорт модели Role
import java.util.List;

public interface RoleService {
    Role getRoleByName(String roleName);
    List<Role> getAllRoles();
}
