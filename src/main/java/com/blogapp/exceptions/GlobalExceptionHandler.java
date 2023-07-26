package com.blogapp.exceptions;

import com.blogapp.response.Response;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception){
        Map<String, String> response = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((objectError -> {
            String fieldName = ((FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();
            response.put(fieldName, message);
        }));
        return ResponseEntity.ok(
                Response.builder()
                        .responseTime(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Input Fields are Not Valid!")
                        .data(Collections.singletonMap("error", response))
                        .build()
        );
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleMethodArgumentTypeMismatchException (MethodArgumentTypeMismatchException exception){
        Map<String, String> response = new HashMap<>();
            String fieldName = exception.getName();
            String message = exception.getMessage();
            response.put(fieldName, message);
        return ResponseEntity.ok(
                Response.builder()
                        .responseTime(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Arguments types are Mismatch!")
                        .data(Collections.singletonMap("error", response))
                        .build()
        );
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response> handleMissingServletRequestParameterException (MissingServletRequestParameterException exception){
        Map<String, String> response = new HashMap<>();
        String parameterName = exception.getParameterName();
        String message = exception.getMessage();
        response.put(parameterName, message);
        return ResponseEntity.ok(
                Response.builder()
                        .responseTime(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Missing ServletRequest Parameter!")
                        .data(Collections.singletonMap("error", response))
                        .build()
        );
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> handleDataIntegrityViolationException (DataIntegrityViolationException exception){
        Map<String, String> response = new HashMap<>();
        String name = "DataIntegrityViolationException";
        String message = exception.getMessage();
        response.put(name, message);
        return ResponseEntity.ok(
                Response.builder()
                        .responseTime(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("DataIntegrity Violation!")
                        .data(Collections.singletonMap("error", response))
                        .build()
        );
    }
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<Response> handleFileSizeLimitExceededException (FileSizeLimitExceededException exception){
        Map<String, String> response = new HashMap<>();
        String name = "FileSizeLimitExceeded";
        String message = exception.getMessage();
        response.put(name, message);
        return ResponseEntity.ok(
                Response.builder()
                        .responseTime(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("FileSizeLimitExceededException!")
                        .data(Collections.singletonMap("error", response))
                        .build()
        );
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleHttpMessageNotReadableException (HttpMessageNotReadableException exception){
        Map<String, String> response = new HashMap<>();
        String name = "HttpMessageNotReadableException";
        String message = exception.getMessage();
        response.put(name, message);
        return ResponseEntity.ok(
                Response.builder()
                        .responseTime(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("HttpMessage Not Readable!")
                        .data(Collections.singletonMap("error", response))
                        .build()
        );
    }
}