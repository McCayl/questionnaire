package com.mccayl.questionnaire.controller;

import com.mccayl.questionnaire.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("area")
public class AreaController {
    private final AreaService areaService;
    @GetMapping
    public String getAreaTestPage(Model model) {
        model.addAttribute("areas", areaService.getAll());
        return "area/areas";
    }
}
