package com.mccayl.questionnaire.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ThemeServiceErrorAdvice {
    @ResponseBody
    @ExceptionHandler(ThemeNotDeletedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String themeNotDeletedHandler(ThemeNotDeletedException ex) {
        return ex.getMessage();
    }
}
