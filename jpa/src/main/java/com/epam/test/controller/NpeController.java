package com.epam.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NpeController {

    @GetMapping("npe")
    public String product() {
        return getNull().toString();
    }

    private Object getNull() {
        return null;
    }
}
