package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.dto.TestScoreDTO;
import com.mccayl.questionnaire.model.Theme;
import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.model.UserTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface UserService {
    User save(User user);

    Page<TestScoreDTO> getUserScore(Long uid, Pageable pageable);
    Page<UserTest> getUserInfoById(Long uid, Pageable pageable);
}
