package com.mccayl.questionnaire.controller;

import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.UserTest;
import com.mccayl.questionnaire.service.TestService;
import com.mccayl.questionnaire.service.UserTestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/test")
@AllArgsConstructor
public class TestController {
    private TestService testService;
    private UserTestService userTestService;

    @PostMapping("complete")
    public UserTest save(@RequestBody UserTest userTest) {
        return userTestService.save(userTest);
    }

    @GetMapping("{id}")
    public Test getTest(@PathVariable Long id) {
        return testService.getOne(id);
    }

    @PostMapping
    public Test addTest(@RequestBody Test test) {
        return testService.save(test);
    }

    @PutMapping("{id}")
    public Test updTest(@PathVariable Long id,
                        @RequestBody Test test) {
        return testService.edit(id, test);
    }

    @DeleteMapping("{id}")
    public void delTest(@PathVariable Long id) {
        testService.del(id);
    }
}
