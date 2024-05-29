package com.mccayl.questionnaire.controller;

import com.mccayl.questionnaire.dto.ExecutingTestDTO;
import com.mccayl.questionnaire.model.*;
import com.mccayl.questionnaire.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    private final UserTestService userTestService;
    private final JwtService jwtService;
    private final AreaService areaService;
    private final QuestionService questionService;


    @GetMapping("{testId}/update")
    public String updateTestPage(@PathVariable Long testId,
                                 @RequestParam Long themeId,
                                 Model model) {
        Test test = testService.getOne(testId);
        model.addAttribute("test", test);
        model.addAttribute("themeId", themeId);
        return "test/updTest";
    }

    @GetMapping("save")
    public String saveTest(@RequestParam Long themeId,
                           Model model) {
        Test test = new Test();
        model.addAttribute("test", test);
        model.addAttribute("themeId", themeId);
        return "test/updTest";
    }

    @PostMapping("update")
    public String updateTest(@RequestParam Long themeId,
                             @ModelAttribute("test") Test test) {
        if (test.getId() == null) {
            testService.save(themeId, test);
        } else {
            testService.edit(test.getId(), test);
        }
        return "redirect:/theme/" + themeId + "/tests";
    }

    @PostMapping("{testId}/delete")
    public String deleteTest(@PathVariable Long testId,
                             @RequestParam Long themeId) {
        testService.del(testId);
        return "redirect:/theme/" + themeId + "/tests";
    }

    @GetMapping("questions")
    public String getQuestionsPage(@PathVariable Long testId,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   Model model) {
        model.addAttribute("questions",
                testService.getQuestionsByTestId(testId, PageRequest.of(page - 1, size)));
        model.addAttribute("themeId", testService.getThemeIdByTestId(testId));
        model.addAttribute("url", "test/" + testId + "/questions");
        return "test/questions";
    }

    @GetMapping("save/question")
    public String saveQuestionPage(Model model) {
        Question question = new Question();
        model.addAttribute("question", question);

        return "test/updQuestion";
    }

    @GetMapping("{testId}/update/question/{questionId}")
    public String updateQuestionPage(@PathVariable Long testId,
                                     @PathVariable Long questionId,
                                     Model model) {
        Question question = testService.getQuestion(questionId);
        model.addAttribute("question", question);
        model.addAttribute("testId", testId);
        return "test/updQuestion";
    }

    @PostMapping("update/question")
    public String updateQuestion(@RequestParam Long areaId,
                                 Question question) {
        question.setArea(areaService.getAreaById(areaId));
        questionService.saveQuestion(question);

        return "redirect:/";
    }

    @PostMapping("{testId}/questions/{questionId}/delete")
    public String deleteQuestion(@PathVariable Long testId,
                                 @PathVariable Long questionId) {
        testService.delQuestion(testId, questionId);
        return "redirect:/test/{testId}/questions";
    }

    @GetMapping("{testId}/questions/{questionId}/answers")
    public String getAnswersPage(@PathVariable Long testId,
                                 @PathVariable Long questionId,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 Model model) {
        model.addAttribute("answers",
                testService.getAnswersByQuestionId(questionId, PageRequest.of(page - 1, size)));
        model.addAttribute("testId", testId);
        model.addAttribute("url", "test/" + testId + "/questions" + questionId + "/answers");
        return "test/answers";
    }

    @GetMapping("{testId}/questions/{questionId}/update/answer/{answerId}")
    public String updateAnswerPage(@PathVariable Long testId,
                                   @PathVariable Long answerId,
                                   @PathVariable Long questionId,
                                   Model model) {
        Answer answer = testService.getAnswer(answerId);
        model.addAttribute("answer", answer);
        model.addAttribute("testId", testId);
        model.addAttribute("questionId", questionId);
        return "test/updAnswer";
    }

    @GetMapping("{testId}/questions/{questionId}/save/answer")
    public String saveAnswerPage(@PathVariable Long testId,
                                 @PathVariable Long questionId,
                                 Model model) {
        Answer answer = new Answer();
        model.addAttribute("answer", answer);
        model.addAttribute("testId", testId);
        model.addAttribute("questionId", questionId);
        return "test/updAnswer";
    }

    @PostMapping("{testId}/questions/{questionId}/update/answer")
    public String updateAnswer(@PathVariable Long testId,
                               @PathVariable Long questionId,
                               @ModelAttribute("answer") Answer answer) {
        if (answer.getId() == null)
            testService.addAnswer(testId, questionId, answer);
        else
            testService.editAnswer(testId, answer.getId(), answer);
        return "redirect:/test/{testId}/questions/{questionId}/answers";
    }

    @PostMapping("{testId}/questions/{questionId}/delete/answer/{answerId}")
    public String deleteAnswer(@PathVariable Long testId,
                               @PathVariable Long answerId) {
        testService.delAnswer(testId, answerId);
        return "redirect:/test/{testId}/questions/{questionId}/answers";
    }

    @GetMapping("{testId}/before")
    public String beforeCompletionPage(@PathVariable Long testId,
                                       @AuthenticationPrincipal User user,
                                       Model model) {
        Test test = testService.getOne(testId);
        String token = jwtService.generateToken(user, test.getRuntimeInMin());

        model.addAttribute("token", token);
        model.addAttribute("test", test);

        return "test/beforeCompletion";
    }

    @GetMapping("{testId}/executing")
    public String executingTestPage(@PathVariable Long testId,
                                    @RequestParam(required = false) Optional<Long> userTestId,
                                    @RequestParam String token,
                                    @AuthenticationPrincipal User user,
                                    Model model) {
        Test test = testService.getOne(testId);
        UserTest userTest = userTestService.getUserTestByOptionalId(userTestId, test, user);
        String afterCompletionToken = jwtService.generateToken(user, test.getRuntimeInMin() + 1);

        List<Question> questions = userTestService
                .getNotCompletedQuestions(testId, userTest);

        if (!questions.isEmpty()) {
            Long executeTime = jwtService.extractExpiration(token).getTime();

            model.addAttribute("executingTest",
                    userTestService.getExecutingTestInfo(questions, userTest));
            model.addAttribute("token", token);
            model.addAttribute("afterCompletionToken", afterCompletionToken);
            model.addAttribute("testId", testId);
            model.addAttribute("executeTime", executeTime);
            return "test/testExecuting";
        } else {
            return "redirect:/test/{testId}/after?" + "userTestId=" + userTestId.get() + "&" + "token=" + afterCompletionToken;
        }
    }

    @PostMapping("{testId}/executing/save")
    public String executingTest(@RequestParam Long userTestId,
                                @RequestParam String token,
                                @ModelAttribute("executingTest") ExecutingTestDTO executingTestDTO) {
        userTestService.saveUserResults(executingTestDTO);
        return "redirect:/test/{testId}/executing?" + "userTestId=" + userTestId + "&" + "token=" + token;
    }

    @GetMapping("{testId}/after")
    public String afterCompletionPage(@RequestParam(required = false) Optional<Long> userTestId,
                                      @RequestParam String token,
                                      Model model) {
        model.addAttribute("userScore",
                userTestService.getUserScoreByOptionalUtId(userTestId));
        return "test/afterCompletion";
    }
}
