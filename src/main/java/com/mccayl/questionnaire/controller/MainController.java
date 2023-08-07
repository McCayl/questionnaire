package com.mccayl.questionnaire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/registration")
    public String signup() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
