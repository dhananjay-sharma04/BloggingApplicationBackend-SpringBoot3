package com.blogapp.controllers;

import com.blogapp.dto.UsrDto;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.response.Response;
import com.blogapp.services.impl.UsrSvcImpl;
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
@RequestMapping(value = "/users")
public class UsrCntlr {
    @Autowired
    private UsrSvcImpl usrSvc;
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getUsrList(@RequestParam @Valid int page, @RequestParam @Valid int pageSize) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Users list fetched successfully!")
                            .method("UsrCntlr.getUsrList")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("users", usrSvc.getUsrList(page, pageSize)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("UsrCntlr.getUsrList")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createUsr(@RequestBody @Valid UsrDto usrDto) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User Added successfully!")
                            .method("UsrCntlr.createUsr")
                            .executionMessage("Implemented business logic of Simple Search class method")
                            .data(Collections.singletonMap("users", usrSvc.createUsr(usrDto)))
                            .build()
            );
        } catch (ResourceAlreadyExists resourceAlreadyExists) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceAlreadyExists.getMessage())
                            .method("UsrCntlr.createUsr")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateUsr(@RequestBody @Valid UsrDto usrDto, @PathVariable("id") @Valid Long id)  {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User updated successfully!")
                            .method("UsrCntlr.updateUsr")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("users", usrSvc.updateUsr(id, usrDto)))
                            .build()
            );
        } catch (ResourceNotFound | ResourceAlreadyExists exception) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(exception.getMessage())
                            .method("UsrCntlr.updateUsr")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteUser(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User object deleted successfully!")
                            .method("UsrCntlr.deleteUser")
                            .executionMessage("Implemented business logic of service class method")
                            .data(usrSvc.delUsr(id))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("UsrCntlr.deleteUser")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
}