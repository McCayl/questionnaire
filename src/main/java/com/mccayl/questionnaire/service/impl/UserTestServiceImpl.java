package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.dto.ExecutingTestDTO;
import com.mccayl.questionnaire.model.*;
import com.mccayl.questionnaire.repo.QuestionRepository;
import com.mccayl.questionnaire.repo.UserTestRepository;
import com.mccayl.questionnaire.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserTestServiceImpl implements UserTestService {
    private final UserTestRepository userTestRepository;
    private final QuestionRepository questionRepository;

    @Override
    public UserTest save(UserTest userTest) {
        return userTestRepository.saveAndFlush(userTest);
    }

    @Override
    public void saveUserResults(ExecutingTestDTO executingTestDTO) {
        UserTest userTest = executingTestDTO.getUserTest();
        Long userScore = userTest.getUserScore();
        Test test = userTest.getTest();
        Question completedQuestion = executingTestDTO.getQuestion();
        List<Answer> userSelectedAnswers =
                getUserAnswersByIds(executingTestDTO.getUserAnswers());

        userScore += countUserScore(test.getCoeffOfCorrectAnswer(), userSelectedAnswers);
        userTest.setUserScore(userScore);
        userTest.getQuestions().add(completedQuestion);
        userTest.getAnswers().addAll(userSelectedAnswers);
        save(userTest);
    }

    @Override
    public UserTest getById(Long userTestId) {
        return userTestRepository.findUserTestById(userTestId);
    }

    @Override
    public UserTest getEmptyUserTest(Test test, User user) {
        UserTest userTest = new UserTest();
        userTest.setTest(test);
        userTest.setUser(user);
        userTest.setAnswers(new HashSet<>());
        userTest.setQuestions(new HashSet<>());
        return userTest;
    }

    @Override
    public UserTest getUserTestByOptionalId(Optional<Long> userTestId,
                                            Test test,
                                            User user) {
        UserTest userTest;

        if (userTestId.isEmpty()) {
            userTest = getEmptyUserTest(test, user);
            userTest = save(userTest);
        } else {
            userTest = getById(userTestId.get());
        }

        return userTest;
    }

    @Override
    public List<Question> getNotCompletedQuestions(Long testId, UserTest userTest) {
        List<Question> testQuestions = userTest.getTest().getQuestions();
        Set<Question> completedQuestions = userTest.getQuestions();

        return testQuestions.stream()
                .filter(question -> !completedQuestions.contains(question))
                .toList();
        /*List<Long> completedQIds = userTest.getQuestions().stream().map(Question::getId).toList();
        return questionRepository.findQuestionsByTestIdAndNotIds(testId, completedQIds);*/
    }

    @Override
    public List<Answer> getUserAnswersByIds(List<Long> ids) {
        return userTestRepository.findUserAnswersByIds(ids);
    }

    @Override
    public Integer countCorrectAnswers(List<Answer> answers) {
        return Math.toIntExact(answers.stream()
                .filter(Answer::isCorrect)
                .count());
    }

    @Override
    public long getUserScoreByOptionalUtId(Optional<Long> userTestId) {
        long userScore = 0;

        if (userTestId.isPresent())
            userScore = getById(userTestId.get()).getUserScore();

        return userScore;
    }

    @Override
    public Long countUserScore(Float coeffOfCorrectAnswer,
                               List<Answer> userAnswers) {
        int correctAnswers = countCorrectAnswers(userAnswers);
        int incorrectAnswers = userAnswers.size() - correctAnswers;
        long result = (long) Math.round(correctAnswers * coeffOfCorrectAnswer);

        return incorrectAnswers == 0 ? result : 0L;
    }

    @Override
    public Boolean isAnyTestCompleted(List<Test> tests) {
        for (int i = 0; i != tests.size(); i++) {
            Test test = tests.get(i);
            Optional<Test> completedTest = userTestRepository
                    .getTestById(test.getId());

            if (completedTest.isPresent())
                return true;
        }

        return false;
    }

    @Override
    public ExecutingTestDTO getExecutingTestInfo(List<Question> notCompletedQuestions,
                                                 UserTest userTest) {
        Question question = getRandomQuestion(notCompletedQuestions);
        List<Answer> answers = question.getAnswers();
        int numberOfCorrectAnswers = countCorrectAnswers(answers);
        List<Long> userAnswers = new ArrayList<>();

        return new ExecutingTestDTO(
                question,
                answers,
                numberOfCorrectAnswers,
                userAnswers,
                userTest);
    }

    @Override
    public Question getRandomQuestion(List<Question> questions) {
        return questions.get(new Random().nextInt(questions.size()));
    }
}
