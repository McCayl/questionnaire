package com.mccayl.questionnaire.controller.rest;

import com.mccayl.questionnaire.dto.UserScoreDTO;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.Theme;
import com.mccayl.questionnaire.service.ThemeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/theme")
@AllArgsConstructor
public class ThemeController {
    private ThemeService themeService;

    @GetMapping
    public Page<Theme> getThemes(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        return themeService.getThemes(PageRequest.of(page - 1, size));
    }

    @GetMapping("rating/{themeId}")
    public List<UserScoreDTO> getUserRating(@PathVariable Long themeId) {
        return themeService.getUserRating(themeId);
    }

    @GetMapping("{themeId}/tests")
    public Page<Test> getAvailableTests(@PathVariable Long themeId,
                                        @RequestParam Long uid,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        return themeService.getAvailableTests(themeId, uid, PageRequest.of(page - 1, size));
    }

    @PostMapping
    public Theme save(@RequestBody Theme theme) {
        return themeService.save(theme);
    }
}
