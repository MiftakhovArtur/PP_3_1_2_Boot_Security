package ru.kata.spring.boot_security.demo.controller;

import jakarta.validation.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import ru.kata.spring.boot_security.demo.models.*;
import ru.kata.spring.boot_security.demo.services.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView registrationForm() {
        ModelAndView mv = new ModelAndView("security/registration");
        mv.addObject("user", new User());
        return mv;
    }

    @PostMapping("/registration")
    public ModelAndView performRegistration(@ModelAttribute("user") @Valid User user,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("security/registration");
            mv.addObject("user", user);
            return mv;
        }

        userService.save(user);
        return new ModelAndView("redirect:/auth/login");
    }

    @GetMapping("/login")
    public String loginPage() {
        return "security/login";
    }
}
