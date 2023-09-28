package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.dto.ExecutingTestDTO;
import com.mccayl.questionnaire.model.*;

import java.util.List;
import java.util.Optional;

public interface UserTestService {
    UserTest save(UserTest userTest);
    void saveUserResults(ExecutingTestDTO executingTestDTO);
    UserTest getById(Long userTestId);
    UserTest getEmptyUserTest(Test test, User user);
    UserTest getUserTestByOptionalId(Optional<Long> userTestId,
                                     Test test,
                                     User user);
    List<Question> getNotCompletedQuestions(Long testId, UserTest userTest);
    List<Answer> getUserAnswersByIds(List<Long> ids);
    Integer countCorrectAnswers(List<Answer> answers);
    long getUserScoreByOptionalUtId(Optional<Long> userTestId);
    Long countUserScore(Float coeffOfCorrectAnswer,
                        List<Answer> userAnswers);
    Boolean isAnyTestCompleted(List<Test> tests);
    ExecutingTestDTO getExecutingTestInfo(List<Question> notCompletedQuestions,
                                          UserTest userTest);
    Question getRandomQuestion(List<Question> questions);
}
