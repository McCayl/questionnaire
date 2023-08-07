package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.exception.TestNotDeletedException;
import com.mccayl.questionnaire.exception.TestNotEditedException;
import com.mccayl.questionnaire.exception.TestNotFoundException;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.repo.TestRepository;
import com.mccayl.questionnaire.repo.ThemeRepository;
import com.mccayl.questionnaire.repo.UserTestRepository;
import com.mccayl.questionnaire.service.TestService;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private TestRepository testRepository;
    private ThemeRepository themeRepository;
    private UserTestRepository userTestRepository;
    @Override
    public Test getOne(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new TestNotFoundException(id));
    }

    @Override
    public Test save(Test test) {
        if (test.getTheme().getId() == null)
            test.setTheme(themeRepository.saveAndFlush(test.getTheme()));
        return testRepository.saveAndFlush(test);
    }

    @Override
    public Test edit(Long id, Test newTest) {
        if (userTestRepository.getTestById(id).isPresent()) {
            throw new TestNotEditedException(id);
        }
        return testRepository.findById(id).map(test -> {
            test.setName(newTest.getName());
            test.setAvailable(newTest.isAvailable());
            test.setAttempts(newTest.getAttempts());
            test.setTheme(newTest.getTheme());
            test.getQuestions().retainAll(newTest.getQuestions());
            test.getQuestions().addAll(newTest.getQuestions());
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
