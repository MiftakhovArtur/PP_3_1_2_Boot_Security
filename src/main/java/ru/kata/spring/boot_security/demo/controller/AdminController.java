package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String welcome() {
        return "redirect:/admin/all";
    }

    @GetMapping("/all")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "allUsersPage";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/add")
    public String postAddUser(@ModelAttribute("user") User user,
                              @RequestParam(required = false) String roleAdmin,
                              @RequestParam(required = false) String roleVIP) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        if ("ROLE_ADMIN".equals(roleAdmin)) roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        if ("ROLE_VIP".equals(roleVIP)) roles.add(roleService.getRoleByName("ROLE_VIP"));
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        Set<Role> roles = user.getRoles();
        if (roles.contains(roleService.getRoleByName("ROLE_ADMIN"))) model.addAttribute("roleAdmin", true);
        if (roles.contains(roleService.getRoleByName("ROLE_VIP"))) model.addAttribute("roleVIP", true);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/edit")
    public String postEditUser(@ModelAttribute("user") User user,
                               @RequestParam(required = false) String roleAdmin,
                               @RequestParam(required = false) String roleVIP) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        if ("ROLE_ADMIN".equals(roleAdmin)) roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        if ("ROLE_VIP".equals(roleVIP)) roles.add(roleService.getRoleByName("ROLE_VIP"));
        user.setRoles(roles);
        userService.editUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
