package com.mccayl.questionnaire.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<Answer> answers;

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void delAnswer(Answer answer) {
        answers.remove(answer);
    }
}
