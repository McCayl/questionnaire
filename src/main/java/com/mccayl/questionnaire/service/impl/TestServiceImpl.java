package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.exception.*;
import com.mccayl.questionnaire.model.Answer;
import com.mccayl.questionnaire.model.Question;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.Theme;
import com.mccayl.questionnaire.repo.*;
import com.mccayl.questionnaire.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final ThemeRepository themeRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserTestRepository userTestRepository;
    @Override
    public Test getOne(Long id) {
        return testRepository.findTestById(id)
                .orElseThrow(() -> new TestNotFoundException(id));
    }

    @Override
    public Long getThemeIdByTestId(Long testId) {
        return testRepository.getThemeIdByTestId(testId);
    }

    @Override
    public Page<Question> getQuestionsByTestId(Long testId, Pageable pageable) {
        return questionRepository.findQuestionsByTestId(testId, pageable);
    }

    @Override
    public Question getQuestion(Long questionId) {
        return questionRepository.findQuestionById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    @Override
    public Question addQuestion(Long testId, Question question) {
        Test test = testRepository.findTestById(testId)
                .orElseThrow(() -> new TestNotFoundException(testId));
        if (userTestRepository.getTestById(testId).isPresent()) {
            throw new TestNotEditedException(testId);
        }
        test.addQuestion(question);
        return questionRepository.saveAndFlush(question);
    }

    @Override
    public Question editQuestion(Long testId, Long questionId, Question newQuestion) {
        if (userTestRepository.getTestById(testId).isPresent()) {
            throw new TestNotEditedException(testId);
        }
        return questionRepository.findQuestionById(questionId).map(question -> {
            question.setText(newQuestion.getText());
            return questionRepository.saveAndFlush(question);
        }).orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    @Override
    public void delQuestion(Long testId, Long questionId) {
        if (userTestRepository.getTestById(testId).isPresent()) {
            throw new TestNotEditedException(testId);
        }
        questionRepository.deleteById(questionId);
    }

    @Override
    public Page<Answer> getAnswersByQuestionId(Long questionId, Pageable pageable) {
        return answerRepository.findAnswersByQuestionId(questionId, pageable);
    }

    @Override
    public Answer getAnswer(Long answerId) {
        return answerRepository.findAnswerById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(answerId));
    }

    @Override
    public Answer addAnswer(Long testId, Long questionId, Answer answer) {
        Question question = questionRepository.findQuestionById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
        if (userTestRepository.getTestById(testId).isPresent()) {
            throw new TestNotEditedException(testId);
        }
        question.addAnswer(answer);
        return answerRepository.saveAndFlush(answer);
    }

    @Override
    public Answer editAnswer(Long testId, Long answerId, Answer newAnswer) {
        if (userTestRepository.getTestById(testId).isPresent()) {
            throw new TestNotEditedException(testId);
        }
        return answerRepository.findAnswerById(answerId).map(answer -> {
            answer.setText(newAnswer.getText());
            answer.setCorrect(newAnswer.isCorrect());
            return answerRepository.saveAndFlush(answer);
        }).orElseThrow(() -> new AnswerNotFoundException(answerId));
    }

    @Override
    public void delAnswer(Long testId, Long answerId) {
        if (userTestRepository.getTestById(testId).isPresent()) {
            throw new TestNotEditedException(testId);
        }
        answerRepository.deleteById(answerId);
    }

    @Override
    public Test save(Long themeId, Test test) {
        Test savedTest = testRepository.saveAndFlush(test);
        Theme theme = themeRepository.findThemeById(themeId);
        theme.addTest(test);
        return savedTest;
    }

    @Override
    public Test edit(Long id, Test newTest) {
        if (userTestRepository.getTestById(id).isPresent()) {
            throw new TestNotEditedException(id);
        }
        return testRepository.findTestById(id).map(test -> {
            test.setName(newTest.getName());
            test.setAvailable(newTest.isAvailable());
            test.setAttempts(newTest.getAttempts());
            test.setRuntimeInMin(newTest.getRuntimeInMin());
            test.setCoeffOfCorrectAnswer(newTest.getCoeffOfCorrectAnswer());
            return testRepository.saveAndFlush(test);
        }).orElseThrow(() -> new TestNotFoundException(id));
    }

    @Override
    public void del(Long id) {
        if (userTestRepository.getTestById(id).isPresent()) {
            throw new TestNotDeletedException(id);
        }
        testRepository.deleteById(id);
    }
}
