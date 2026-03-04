package ru.kata.spring.boot_security.demo.until;

import org.springframework.stereotype.*;
import org.springframework.validation.*;

import java.util.*;

@Component
public class RoleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz) && List.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        if (target == null) {
            errors.reject("roles.null", "Roles list is null");
            return;
        }

        if (!(target instanceof List)) {
            errors.reject("roles.invalidType", "Invalid roles list type");
            return;
        }

        List<?> roles = (List<?>) target;

        if (roles.isEmpty()) {
            errors.rejectValue("roles", "roles.empty", "Roles list should not be empty");
            return;
        }

        for (int i = 0; i < roles.size(); i++) {
            if (!(roles.get(i) instanceof String)) {
                errors.rejectValue("roles", "roles.invalidType", "Invalid role type at index " + i);
                return;
            }
        }

    }
}

