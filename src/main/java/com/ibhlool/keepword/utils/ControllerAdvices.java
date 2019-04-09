package com.ibhlool.keepword.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@ControllerAdvice
public class ControllerAdvices {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(UserPrincipalNotFoundException.class)
    ErrorModel userPrincipalNotFoundExceptionHandler(UserPrincipalNotFoundException e){
        return new ErrorModel("-1",e.getMessage());
    }

}
