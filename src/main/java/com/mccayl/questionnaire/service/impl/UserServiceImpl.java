package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.dto.TestScoreDTO;
import com.mccayl.questionnaire.dto.ThemeRatingDTO;
import com.mccayl.questionnaire.dto.UserScoreDTO;
import com.mccayl.questionnaire.model.*;
import com.mccayl.questionnaire.repo.*;
import com.mccayl.questionnaire.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
    private final UserTestRepository userTestRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Page<TestScoreDTO> getUserScore(Long uid, Pageable pageable) {
        return userRepository.getUserScore(uid, pageable);
    }

    @Override
    public Page<UserTest> getUserInfoById(Long uid, Pageable pageable) {
        return userRepository.getUserInfoById(uid, pageable);
    }

    @Override
    public Page<UserTest> getUserInfoByEmailLikeKeyword(String keyword, Pageable pageable) {
        return userRepository.getUserInfoByEmailLikeKeyword(keyword, pageable);
    }

    @Override
    public Page<UserTest> getAllUserInfo(Pageable pageable) {
        return userRepository.getAllUserInfo(pageable);
    }

    @Override
    public Page<ThemeRatingDTO> getUserRatingByEmail(String email, Pageable pageable) {
        List<Theme> themes = themeRepository.getThemes(pageable).getContent();
        List<ThemeRatingDTO> themeRating = new ArrayList<>();
        Comparator<UserScoreDTO> byScoreDesc = (o1, o2) -> Math.toIntExact(o2.getScore() - o1.getScore());

        for (int i = 0; i != themes.size(); i++) {
            Theme theme = themes.get(i);
            List<UserScoreDTO> userScore = themeRepository
                    .getUserRating(theme.getId(), null);

            userScore.sort(byScoreDesc);
            Optional<UserScoreDTO> optionalUserScoreDTO = userScore.stream()
                    .filter(userScoreDTO -> userScoreDTO.getEmail().equals(email))
                    .findFirst();

            if (optionalUserScoreDTO.isPresent()) {
                UserScoreDTO user = optionalUserScoreDTO.get();
                ThemeRatingDTO themeRatingDTO = new ThemeRatingDTO(theme.getName(),
                        userScore.indexOf(user) + 1,
                        user.getScore());

                themeRating.add(themeRatingDTO);
            }
        }

        return new PageImpl<>(themeRating, pageable, themeRating.size());
    }

    @Override
    public Page<Question> getUserQuestionsByUtId(Long userTestId, Pageable pageable) {
        return userRepository.getUserQuestionsByUtId(userTestId, pageable);
    }

    @Override
    public Page<Answer> getUserAnswersByUtIdAndQId(Long userTestId,
                                                   Long qId,
                                                   Pageable pageable) {
        Question question = questionRepository.findQuestionById(qId).get();
        UserTest userTest = userTestRepository.findUserTestById(userTestId);

        List<Long> qanswersIds = new java.util.ArrayList<>(question.getAnswers()
                .stream().map(Answer::getId)
                .toList());

        List<Long> uanswersIds = userTest.getAnswers()
                .stream().map(Answer::getId)
                .toList();

        qanswersIds.retainAll(uanswersIds);

        return answerRepository.findAnswersByIds(qanswersIds, pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user " + username));
    }
}
