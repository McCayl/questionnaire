package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.dto.TestScoreDTO;
import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.model.UserTest;
import com.mccayl.questionnaire.repo.UserRepository;
import com.mccayl.questionnaire.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Override
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Page<TestScoreDTO> getUserScore(Long uid, Pageable pageable) {
        return userRepository.getUserScore(uid, pageable);
    }

    @Override
    public Page<UserTest> getUserInfoById(Long uid, Pageable pageable) {
        return userRepository.getUserInfoById(uid, pageable);
    }

}
