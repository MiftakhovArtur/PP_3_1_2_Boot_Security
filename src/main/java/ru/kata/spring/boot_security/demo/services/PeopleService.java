package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repository.PeopleRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> loadUserByUsername(String username) throws UsernameNotFoundException {
        return peopleRepository.findByUsername(username);
    }

    public Set<Role> getUserRoles(String username) {
        Optional<Person> userOptional = peopleRepository.findByUsername(username);
        return userOptional.map(Person::getRoles).orElse(Collections.emptySet());
    }
}

