package com.mccayl.questionnaire.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "theme", orphanRemoval = true)
    private List<Test> tests = new ArrayList<>();
    public void addTest(Test test) {
        tests.add(test);
        test.setTheme(this);
    }

    public void delTest(Test test) {
        tests.remove(test);
        test.setTheme(null);
    }
}
