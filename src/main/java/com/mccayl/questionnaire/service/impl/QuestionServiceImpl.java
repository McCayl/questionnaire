package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.model.Question;
import com.mccayl.questionnaire.repo.QuestionRepository;
import com.mccayl.questionnaire.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository repository;

    @Override
    public void saveQuestion(Question question) {
        repository.save(question);
    }
}
