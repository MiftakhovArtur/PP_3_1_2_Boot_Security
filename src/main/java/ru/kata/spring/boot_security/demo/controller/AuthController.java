package ru.kata.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

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
