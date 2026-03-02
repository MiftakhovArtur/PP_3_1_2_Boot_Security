package ru.kata.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

// import javax.annotation.PostConstruct;  // убрать
import jakarta.annotation.PostConstruct;
import java.util.Set;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(Set.of(adminRole, userRole));

        userRepository.save(admin);
    }
}