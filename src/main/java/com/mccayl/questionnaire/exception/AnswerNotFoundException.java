package com.mccayl.questionnaire.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super("Could not find answer " + id);
    }
}
