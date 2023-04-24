package ru.alex0d.javaspring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alex0d.javaspring.services.UserService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,Model model) {
        try {
            if (userService.registerUser(username, password)) {
                model.addAttribute("message", "Регистрация прошла успешно!");
                return "login";
            } else {
                model.addAttribute("error", "Пользователь с таким именем уже существует!");
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка регистрации: " + e.getMessage());
            return "register";
        }
    }
}
