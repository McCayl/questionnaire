package com.mccayl.questionnaire.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super("Could not find question " + id);
    }
}
