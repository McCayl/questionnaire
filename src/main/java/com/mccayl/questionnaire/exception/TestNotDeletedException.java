package com.mccayl.questionnaire.exception;

public class TestNotDeletedException extends RuntimeException {
    public TestNotDeletedException(Long id) {
        super("Cant delete test " + id + " it was done by user");
    }
}
