package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.dto.TestScoreDTO;
import com.mccayl.questionnaire.dto.ThemeRatingDTO;
import com.mccayl.questionnaire.model.Answer;
import com.mccayl.questionnaire.model.Question;
import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.model.UserTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User save(User user);
    Page<TestScoreDTO> getUserScore(Long uid, Pageable pageable);
    Page<UserTest> getUserInfoById(Long uid, Pageable pageable);
    Page<UserTest> getUserInfoByEmailLikeKeyword(String keyword, Pageable pageable);
    Page<UserTest> getAllUserInfo(Pageable pageable);
    Page<ThemeRatingDTO> getUserRatingByEmail(String email, Pageable pageable);
    Page<Question> getUserQuestionsByUtId(Long userTestId, Pageable pageable);
    Page<Answer> getUserAnswersByUtIdAndQId(Long userTestId,
                                            Long qId,
                                            Pageable pageable);
}
