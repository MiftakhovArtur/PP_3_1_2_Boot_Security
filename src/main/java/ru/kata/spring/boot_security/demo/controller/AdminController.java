package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView listUsers() {
        ModelAndView mav = new ModelAndView("admin/users");
        mav.addObject("users", userService.findAll());
        return mav;
    }

    @GetMapping("/users/{id}")
    public ModelAndView getUser(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("admin/userDetails");
        User user = userService.findById(id);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/users/save")
    public ModelAndView saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return new ModelAndView("redirect:/admin/users");
    }

    @PostMapping("/users/update")
    public ModelAndView updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return new ModelAndView("redirect:/admin/users");
    }

    @PostMapping("/users/delete/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        userService.delete(user);
        return new ModelAndView("redirect:/admin/users");
    }
}
