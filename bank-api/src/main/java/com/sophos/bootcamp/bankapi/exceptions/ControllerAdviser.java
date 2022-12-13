package com.sophos.bootcamp.bankapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerAdviser {

    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody
    ResponseEntity<Error> handleApiException(NotFoundException exception) {

        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    public @ResponseBody
    ResponseEntity<Error> handleBadRequestException(BadRequestException exception) {

        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.BAD_REQUEST);

    }


}
