package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.model.Test;

public interface TestService {
    Test getOne(Long id);
    Test save(Test test);
    Test edit(Long id, Test newTest);
    void del(Long id);
}
