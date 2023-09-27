package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.dto.UserRatingDTO;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThemeService {
    Page<Theme> getThemes(Pageable pageable);
    Theme getThemeById(Long themeId);
    List<UserRatingDTO> getUserRating(Long themeId, String email);
    Page<Test> getAvailableTests(Long themeId, Long uid, Pageable pageable);
    Page<Test> getTests(Long themeId, Pageable pageable);
    Theme save(Theme theme);
    void del(Long themeId);
}
