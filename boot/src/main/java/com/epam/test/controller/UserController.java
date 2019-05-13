package com.epam.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.test.dto.UserRegistrationFormDto;
import com.epam.test.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @RequestMapping("registration")
    public String registrationForm(Model model) {
        model.addAttribute(UserRegistrationFormDto.MODEL, new UserRegistrationFormDto());
        return "registration";
    }

    @PostMapping
    public String createUser(UserRegistrationFormDto formDto) {
        userService.create(formDto);
        return "redirect:/";
    }
}
