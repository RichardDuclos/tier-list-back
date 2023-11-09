package com.richardduclos.tierlist.controllers.advice;

import com.richardduclos.tierlist.exceptions.MvcEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MvcEntityNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(MvcEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String MvcEntityNotFoundHandler(MvcEntityNotFoundException ex) {
    return ex.getMessage();
    }
}