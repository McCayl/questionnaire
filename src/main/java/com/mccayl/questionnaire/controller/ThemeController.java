package com.mccayl.questionnaire.controller;

import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.Theme;
import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("theme")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;
    @GetMapping
    public String getThemesPage(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "5") int size,
                                Model model) {
        model.addAttribute("url", "theme");
        model.addAttribute("themes", themeService.getThemes(PageRequest.of(page - 1, size)));
        return "theme/theme";
    }

    @GetMapping("update/{id}")
    public String updateThemePage(@PathVariable Long id, Model model) {
        Theme theme = themeService.getThemeById(id);
        model.addAttribute("theme", theme);
        return "theme/updTheme";
    }

    @GetMapping("save")
    public String saveThemePage(Model model) {
        Theme theme = new Theme();
        model.addAttribute("theme", theme);
        return "theme/updTheme";
    }

    @PostMapping("save")
    public String saveTheme(@ModelAttribute("theme") Theme theme) {
        themeService.save(theme);
        return "redirect:/theme";
    }

    @PostMapping("{themeId}/delete")
    public String deleteTheme(@PathVariable Long themeId) {
        themeService.del(themeId);
        return "redirect:/theme";
    }

    @GetMapping("{id}/tests")
    public String getThemeTestsPage(@PathVariable Long id,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "5") int size,
                                    @AuthenticationPrincipal User user,
                                    HttpServletRequest request,
                                    Model model) {
        Page<Test> tests = request.isUserInRole("ROLE_ADMIN") ?
                themeService.getTests(id, PageRequest.of(page - 1, size)) :
                themeService.getAvailableTests(id, user.getId(),
                        PageRequest.of(page - 1, size));

        model.addAttribute("url", "theme/" + id + "/tests");
        model.addAttribute("themeId", id);
        model.addAttribute("tests", tests);
        model.addAttribute("userRating", themeService
                .getUserRating(id, user.getEmail()));
        return "test/tests";
    }
}
