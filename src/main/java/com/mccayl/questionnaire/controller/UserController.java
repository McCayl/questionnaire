package com.mccayl.questionnaire.controller;

import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.service.UserService;
import com.mccayl.questionnaire.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final UserTestService userTestService;

    @GetMapping("profile")
    public String userProfilePage(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @AuthenticationPrincipal User user,
                                  Model model) {
        model.addAttribute("userScore",
                userService.getUserScore(user.getId(), PageRequest.of(page - 1, size)));
        model.addAttribute("url", "user/profile");
        return "user/userProfile";
    }

    @GetMapping("admin/usertest")
    public String adminPage(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(required = false) Optional<String> keyword,
                            Model model) {
        if (keyword.isPresent()) {
            model.addAttribute("userInfo",
                    userService.getUserInfoByEmailLikeKeyword(keyword.get(), PageRequest.of(page - 1, size)));
            model.addAttribute("url",
                    "user/admin/usertest");
        } else {
            model.addAttribute("userInfo",
                    userService.getAllUserInfo(PageRequest.of(page - 1, size)));
            model.addAttribute("url",
                    "user/admin/usertest");
        }

        return "user/adminPage";
    }

    @GetMapping("admin/usertest/{utId}/rating")
    public String adminUserRatingPage(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "5") int size,
                                      @PathVariable Long utId,
                                      Model model) {
        User user = userTestService.getById(utId).getUser();

        model.addAttribute("rating",
                userService.getUserRatingByEmail(user.getEmail(), PageRequest.of(page - 1, size)));
        model.addAttribute("url", "user/admin/usertest/" + utId +  "/rating");

        return "user/userRating";
    }

    @GetMapping("admin/usertest/{utId}/questions")
    public String adminUserQuestionsPage(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "5") int size,
                                         @PathVariable Long utId,
                                         Model model) {
        model.addAttribute("questions",
                userService.getUserQuestionsByUtId(utId, PageRequest.of(page - 1, size)));
        model.addAttribute("url", "user/admin/usertest/" + utId + "/questions");
        return "user/userQuestions";
    }

    @GetMapping("admin/usertest/{utId}/questions/{qId}/answers")
    public String adminUserAnswersPage(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "5") int size,
                                       @PathVariable Long utId,
                                       @PathVariable Long qId,
                                       Model model) {
        model.addAttribute("answers",
                userService.getUserAnswersByUtIdAndQId(utId, qId, PageRequest.of(page - 1, size)));
        model.addAttribute("url",
                "user/admin/usertest/" +
                utId + "/questions/" +
                qId + "/answers");
        return "user/userAnswers";
    }
}
