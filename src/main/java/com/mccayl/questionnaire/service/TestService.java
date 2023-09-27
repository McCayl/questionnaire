package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.model.Answer;
import com.mccayl.questionnaire.model.Question;
import com.mccayl.questionnaire.model.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {
    Test getOne(Long id);
    Page<Question> getQuestionsByTestId(Long testId, Pageable pageable);
    Question getQuestion(Long questionId);
    Question addQuestion(Long testId, Question question);
    Question editQuestion(Long testId, Long questionId, Question newQuestion);
    void delQuestion(Long testId, Long questionId);
    Page<Answer> getAnswersByQuestionId(Long questionId, Pageable pageable);
    Answer getAnswer(Long answerId);
    Answer addAnswer(Long testId, Long questionId, Answer answer);
    Answer editAnswer(Long testId, Long answerId, Answer newAnswer);
    void delAnswer(Long testId, Long answerId);
    Test save(Long themeId, Test test);
    Test edit(Long id, Test newTest);
    void del(Long id);
}
