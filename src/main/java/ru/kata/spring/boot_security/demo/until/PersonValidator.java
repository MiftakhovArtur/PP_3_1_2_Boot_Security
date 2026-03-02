package ru.kata.spring.boot_security.demo.until;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.services.PeopleService;

import java.util.List;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.loadUserByUsername(person.getUsername()).isPresent()
                && !peopleService.loadUserByUsername(person.getUsername()).orElse(null).equals(target)) {
            errors.rejectValue("firstName", "", "A user with that name already exists");
        }
    }
}

