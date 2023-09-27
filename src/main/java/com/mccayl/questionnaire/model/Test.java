package com.mccayl.questionnaire.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean available;
    private int attempts;
    private int runtimeInMin;
    private Float coeffOfCorrectAnswer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    @ToString.Exclude
    private Theme theme;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "test_id")
    private List<Question> questions;

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void delQuestion(Question question) {
        questions.remove(question);
    }
}
