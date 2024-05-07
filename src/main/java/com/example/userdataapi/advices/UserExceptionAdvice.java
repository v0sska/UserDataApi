package com.example.userdataapi.advices;

import com.example.userdataapi.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class UserExceptionAdvice {


    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> userExceptionHandler(UserException exception){
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.CONFLICT);
    }

}
