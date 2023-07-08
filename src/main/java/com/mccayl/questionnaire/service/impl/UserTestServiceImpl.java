package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.model.UserTest;
import com.mccayl.questionnaire.repo.UserTestRepository;
import com.mccayl.questionnaire.service.UserTestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserTestServiceImpl implements UserTestService {
    private UserTestRepository userTestRepository;

    @Override
    public UserTest save(UserTest userTest) {
        return userTestRepository.saveAndFlush(userTest);
    }
}
