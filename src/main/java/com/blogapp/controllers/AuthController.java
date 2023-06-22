package com.blogapp.controllers;

import com.blogapp.response.Response;
import com.blogapp.securities.AuthSvcImpl;
import com.blogapp.securities.JwtAuthRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    private AuthSvcImpl authSvc;
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> login(@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User login successfully!")
                            .method("AuthController.login")
                            .executionMessage("Implemented business logic of Simple Search class method")
                            .data(Collections.singletonMap("login", authSvc.authenticate(jwtAuthRequest)))
                            .build()
            );
        } catch (Exception exception) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(exception.getMessage())
                            .method("AuthController.login")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
}