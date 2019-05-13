package com.epam.test.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Controller
public class HomeController {

    private final Counter counter;

    public HomeController(MeterRegistry registry) {
        this.counter = registry.counter("home.page.visit");
    }

    @ModelAttribute("module")
    String module() {
        return "home";
    }

    @GetMapping("/")
    String index(Principal principal) {
        counter.increment();
        return "home";
    }
}
