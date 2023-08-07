package com.mccayl.questionnaire.controller.rest;

import com.mccayl.questionnaire.dto.TestScoreDTO;
import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.model.UserTest;
import com.mccayl.questionnaire.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("score")
    public Page<TestScoreDTO> getUserScore(@RequestParam Long uid,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        return userService.getUserScore(uid, PageRequest.of(page - 1, size));
    }

    @GetMapping("info")
    public Page<UserTest> getUserInfoById(@RequestParam Long uid,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        return userService.getUserInfoById(uid, PageRequest.of(page - 1, size));
    }
}
