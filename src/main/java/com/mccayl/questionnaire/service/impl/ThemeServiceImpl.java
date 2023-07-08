package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.dto.UserScoreDTO;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.Theme;
import com.mccayl.questionnaire.repo.ThemeRepository;
import com.mccayl.questionnaire.service.ThemeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private ThemeRepository themeRepository;

    @Override
    public Page<Theme> getThemes(Pageable pageable) {
        return themeRepository.getThemes(pageable);
    }

    @Override
    public List<UserScoreDTO> getUserRating(Long themeId) {
        return themeRepository.getUserRating(
                themeId,
                PageRequest.of(0, 10, Sort.by("score").descending()));
    }

    @Override
    public Page<Test> getAvailableTests(Long themeId, Long uid, Pageable pageable) {
        return themeRepository.getAvailableTests(themeId, uid, pageable);
    }

    @Override
    public Theme save(Theme theme) {
        return themeRepository.saveAndFlush(theme);
    }
}
