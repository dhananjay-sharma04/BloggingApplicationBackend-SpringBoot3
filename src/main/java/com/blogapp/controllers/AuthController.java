package com.blogapp.controllers;

import com.blogapp.dto.UsrDto;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.response.Response;
import com.blogapp.securities.AuthSvcImpl;
import com.blogapp.securities.JwtAuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication Controller", description = "Api related to login, signUp")
public class AuthController {
    @Autowired
    private AuthSvcImpl authSvc;
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "login",
            summary = "To login in this Application, Call this API",
            description = "login method is HTTP POST mapping so user can login to application."
    )
    public ResponseEntity<Response> login(@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User login successfully!")
                            .method("AuthController.login")
                            .executionMessage("Implemented business logic of service class method")
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
    @PostMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "signUpUsr",
            summary = "To register new User in Application, Call this API",
            description = "signUpUsr method is HTTP POST mapping so to store data in database."
    )
    public ResponseEntity<Response> signUpUsr(@RequestBody @Valid UsrDto usrDto) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("SignUp successfully!")
                            .method("AuthController.signUpUsr")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("userDetails", authSvc.signUpUsr(usrDto)))
                            .build()
            );
        } catch (ResourceAlreadyExists | ResourceNotFound exception) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(exception.getMessage())
                            .method("AuthController.signUpUsr")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
}