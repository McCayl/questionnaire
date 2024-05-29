package com.mccayl.questionnaire.exception;

public class AreaNotFoundException extends RuntimeException {
    public AreaNotFoundException(Long id) {
        super("Could not find area " + id);
    }
}
