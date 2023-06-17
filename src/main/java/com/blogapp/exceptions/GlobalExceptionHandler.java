package com.blogapp.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception){
        Map<String, String> response = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((objectError -> {
            String fieldName = ((FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();
            response.put(fieldName, message);
        }));
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException (MethodArgumentTypeMismatchException exception){
        Map<String, String> response = new HashMap<>();
            String fieldName = exception.getName();
            String message = exception.getMessage();
            response.put(fieldName, message);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestParameterException (MissingServletRequestParameterException exception){
        Map<String, String> response = new HashMap<>();
        String parameterName = exception.getParameterName();
        String message = exception.getMessage();
        response.put(parameterName, message);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException (DataIntegrityViolationException exception){
        Map<String, String> response = new HashMap<>();
        String name = "DataIntegrityViolationException";
        String message = exception.getMessage();
        response.put(name, message);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException (HttpMessageNotReadableException exception){
        Map<String, String> response = new HashMap<>();
        String name = "HttpMessageNotReadableException";
        String message = exception.getMessage();
        response.put(name, message);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }
}