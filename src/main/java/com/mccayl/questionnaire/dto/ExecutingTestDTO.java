package com.mccayl.questionnaire.dto;

import com.mccayl.questionnaire.model.Answer;
import com.mccayl.questionnaire.model.Question;
import com.mccayl.questionnaire.model.UserTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutingTestDTO {
    @NotBlank
    private Question question;
    @NotBlank
    private List<Answer> answers;
    @NotBlank
    private Integer numberOfCorrectAnswers;
    @NotBlank
    private List<Long> userAnswers;
    @NotBlank
    private UserTest userTest;
}
