package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.model.*;

import java.util.List;

public interface UserTestService {
    UserTest save(UserTest userTest);
    UserTest getById(Long userTestId);
    UserTest getEmptyUserTest(Test test, User user);
    List<Question> getNotCompletedQuestions(Long testId, UserTest userTest);
    List<Answer> getUserAnswersByIds(List<Long> ids);
    Integer countCorrectAnswers(List<Answer> answers);
    Long countUserScore(Float coeffOfCorrectAnswer,
                        List<Answer> userAnswers);
    Boolean isAnyTestCompleted(List<Test> tests);
}
