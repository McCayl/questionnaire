package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.dto.UserScoreDTO;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThemeService {
    Page<Theme> getThemes(Pageable pageable);
    List<UserScoreDTO> getUserRating(Long themeId);
    Page<Test> getAvailableTests(Long themeId, Long uid, Pageable pageable);
    Theme save(Theme theme);
}
