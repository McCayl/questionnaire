package com.mccayl.questionnaire.controller.rest;

import com.mccayl.questionnaire.dto.UserRatingDTO;
import com.mccayl.questionnaire.dto.UserScoreDTO;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.Theme;
import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.service.ThemeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/theme")
@AllArgsConstructor
public class ThemeRestController {
    private ThemeService themeService;

    @GetMapping
    public Page<Theme> getThemes(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        return themeService.getThemes(PageRequest.of(page - 1, size));
    }

    @GetMapping("rating/{themeId}")
    public List<UserRatingDTO> getUserRating(Long themeId, @AuthenticationPrincipal User user) {
        return themeService.getUserRating(themeId, user.getEmail());
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
