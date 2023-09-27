package com.mccayl.questionnaire.exception;

public class ThemeNotDeletedException extends RuntimeException {
    public ThemeNotDeletedException(Long id) {
        super("Cant delete theme " + id + ", some test completed");
    }
}
