package com.example.challengecrud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        //1Crear payload con exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException=new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //2Return response entity
        return new ResponseEntity<>(apiException, badRequest);

    }

    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(ApiNotFoundException e){
        //1Crear payload con exception details
        HttpStatus notFoundRequest = HttpStatus.NOT_FOUND;

        ApiException apiException=new ApiException(
                e.getMessage(),
                notFoundRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //2Return response entity
        return new ResponseEntity<>(apiException, notFoundRequest);

    }

    @ExceptionHandler(value = {ApiAlreadyExistException.class})
    public ResponseEntity<Object> handleApiAlreadyExistException(ApiAlreadyExistException e){
        //1Crear payload con exception details
        HttpStatus alreadyExistRequest = HttpStatus.CONFLICT;

        ApiException apiException=new ApiException(
                e.getMessage(),
                alreadyExistRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //2Return response entity
        return new ResponseEntity<>(apiException, alreadyExistRequest);

    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleConflict(MethodArgumentNotValidException ex, WebRequest request) {
        //String message = "' " + ex.getFieldError().getField() +" ' " + " No puede estar vacio!";
        String message2= ex.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage().toString();
        ApiException apiException=new ApiException(
                message2,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST );
    }
}
