package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.kata.spring.boot_security.demo.models.*;
import ru.kata.spring.boot_security.demo.repository.*;

import java.util.*;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }
}

