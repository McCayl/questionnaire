package com.mccayl.questionnaire.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class TestServiceErrorAdvice {
    @ResponseBody
    @ExceptionHandler(TestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String testNotFoundHandler(TestNotFoundException ex) {
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(TestNotEditedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String testNotEditedHandler(TestNotEditedException ex) {
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(TestNotDeletedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String testNotDeletedHandler(TestNotDeletedException ex) {
        return ex.getMessage();
    }
}
