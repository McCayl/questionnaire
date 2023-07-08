package com.mccayl.questionnaire.controller;

import com.mccayl.questionnaire.model.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("test/new")
    public String testNew(Model model) {
        model.addAttribute("newTest", new Test());
        return "test";
    }
}
