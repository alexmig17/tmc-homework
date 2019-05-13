package com.epam.test.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.test.dto.UserRegistrationDto;
import com.epam.test.service.PersonService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("user")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    @RequestMapping("registration")
    public String registrationForm(Model model) {
        model.addAttribute(UserRegistrationDto.MODEL, new UserRegistrationDto());
        return "registration";
    }

    @PostMapping
    public String createUser(@Valid UserRegistrationDto formDto) {
        personService.create(formDto);
        return "redirect:/";
    }
}
