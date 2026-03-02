package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.models.Person;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername (String username);

}
