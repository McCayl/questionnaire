package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.dto.UserRatingDTO;
import com.mccayl.questionnaire.dto.UserScoreDTO;
import com.mccayl.questionnaire.exception.ThemeNotDeletedException;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.Theme;
import com.mccayl.questionnaire.repo.ThemeRepository;
import com.mccayl.questionnaire.service.ThemeService;
import com.mccayl.questionnaire.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final UserTestService userTestService;
    private final static int NUMBER_OF_RATING_USERS = 10;
    @Override
    public Page<Theme> getThemes(Pageable pageable) {
        return themeRepository.getThemes(pageable);
    }

    @Override
    public Theme getThemeById(Long themeId) {
        return themeRepository.findThemeById(themeId);
    }

    @Override
    public List<UserRatingDTO> getUserRating(Long themeId, String email) {
        List<UserScoreDTO> userScore = themeRepository.getUserRating(themeId, null);
        List<UserRatingDTO> userRating = new ArrayList<>();
        Comparator<UserScoreDTO> byScoreDesc = (o1, o2) -> Math.toIntExact(o2.getScore() - o1.getScore());

        userScore.sort(byScoreDesc);

        int resultSize = Math.min(userScore.size(), NUMBER_OF_RATING_USERS);
        for (int i = 0; i != resultSize; i++) {
            UserScoreDTO userScoreDTO = userScore.get(i);
            userRating.add(new UserRatingDTO(i + 1, userScoreDTO.getEmail(), userScoreDTO.getScore()));
        }

        Optional<UserScoreDTO> optionalUserScoreDTO = userScore.stream()
                .filter(userScoreDTO -> userScoreDTO.getEmail().equals(email))
                .findFirst();

        if (optionalUserScoreDTO.isPresent()) {
            UserScoreDTO user = optionalUserScoreDTO.get();
            UserRatingDTO userRatingDTO = new UserRatingDTO(userScore.indexOf(user) + 1,
                    user.getEmail(),
                    user.getScore());

            if (!userRating.contains(userRatingDTO))
                userRating.add(userRatingDTO);
        }

        return userRating;
    }

    @Override
    public Page<Test> getAvailableTests(Long themeId, Long uid, Pageable pageable) {
        return themeRepository.getAvailableTests(themeId, uid, pageable);
    }

    @Override
    public Page<Test> getTests(Long themeId, Pageable pageable) {
        return themeRepository.getTests(themeId, pageable);
    }

    @Override
    public Theme save(Theme theme) {
        return themeRepository.saveAndFlush(theme);
    }

    @Override
    @Transactional
    public void del(Long themeId) {
        Theme theme = themeRepository.findThemeById(themeId);

        if (userTestService.isAnyTestCompleted(theme.getTests())) {
            throw new ThemeNotDeletedException(themeId);
        }

        themeRepository.deleteById(themeId);
    }
}
