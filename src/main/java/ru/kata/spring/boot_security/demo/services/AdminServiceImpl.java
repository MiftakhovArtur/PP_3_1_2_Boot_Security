package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repository.PeopleRepository;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final PeopleRepository peopleRepository;
    private final RoleRepository roleRepository;

    public AdminServiceImpl(PeopleRepository peopleRepository, RoleRepository roleRepository) {
        this.peopleRepository = peopleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Person> getAllUsers() {
        return peopleRepository.findAll();
    }

    @Override
    public Person findUserByUserName(String username) {
        Optional<Person> user = peopleRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User " + username + " not found");
        return user.get();
    }

    @Override
    public void updateUser(Person person, List<String> roles) {
        Person beforeUpdate = peopleRepository.findById(person.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        person.setPassword(beforeUpdate.getPassword());

        Set<Role> roleSet = roles.stream()
                .map(Long::valueOf)
                .map(id -> roleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Role not found"))) // ✅ здесь заменили getById
                .collect(Collectors.toSet());

        person.setRoles(roleSet);

        peopleRepository.save(person);
    }

    @Override
    public void removeUser(Long id) {
        Person person = peopleRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        peopleRepository.delete(person);
    }

    @Override
    public Person findOneById(Long id) {
        return peopleRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

