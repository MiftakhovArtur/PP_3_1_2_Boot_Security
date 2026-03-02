package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.Security.PersonDetails;
import ru.kata.spring.boot_security.demo.services.AdminService;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.until.PersonValidator;
import ru.kata.spring.boot_security.demo.until.RoleValidator;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final RoleService roleService;
    private final PersonValidator personValidator;
    private final RoleValidator roleValidator;


    @Autowired
    public AdminController(AdminService adminService, RoleService roleService, PersonValidator personValidator, RoleValidator roleValidator) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.personValidator = personValidator;
        this.roleValidator = roleValidator;
    }


    @GetMapping("/users")
    public String getAllUsers(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("personDetails", personDetails);
        Person person = adminService.findUserByUserName(principal.getName());
        model.addAttribute("person", person);
        List<Person> personList = adminService.getAllUsers();
        model.addAttribute("personList", personList);
        return "admin/users";
    }


    @GetMapping("admin/removeUser")
    public String removeUser(@RequestParam("id") Long id) {
        adminService.removeUser(id);
        return "redirect:/admin/users";
    }


    @GetMapping("/admin/updateUser")
    public String getEditUserForm(Model model, @RequestParam("id") Long id) {
        model.addAttribute("person", adminService.findOneById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "admin/userUpdate";
    }


    @PostMapping("/updateUser")
    public String postEditUserForm(@ModelAttribute("person") @Valid Person person,
                                   BindingResult personBindingResult,
                                   @RequestParam(value = "roles", required = false) @Valid List<String> roles,
                                   BindingResult rolesBindingResult,
                                   RedirectAttributes redirectAttributes) {

        System.out.println();
        personValidator.validate(person, personBindingResult);
        if (personBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorsPerson", personBindingResult.getAllErrors());
            return "/admin/userUpdate";
        }


        roleValidator.validate(roles, rolesBindingResult);
        if (rolesBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorsRoles", rolesBindingResult.getAllErrors());
            return "/admin/userUpdate";
        }

        adminService.updateUser(person, roles);
        return "redirect:/admin/users";
    }

}

