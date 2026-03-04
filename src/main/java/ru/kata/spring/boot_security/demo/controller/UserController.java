package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import ru.kata.spring.boot_security.demo.services.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView showAllUsers() {
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users", userService.findAll());
        return mav;
    }
}
