package com.mccayl.questionnaire.exception;

public class TestNotEditedException extends RuntimeException {
    public TestNotEditedException(Long id) {
        super("Cant edit test " + id + " it was done by user");
    }
}
