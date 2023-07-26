package com.blogapp.controllers;

import com.blogapp.dto.UsrDto;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.response.Response;
import com.blogapp.services.impl.UsrSvcImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@RequestMapping(value = "/users")
@Tag(name = "User Controller", description = "Api related to fetch, create, update and delete user")
public class UsrCntlr {
    @Autowired
    private UsrSvcImpl usrSvc;
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "get",
            summary = "To get List of User with pagination, Call this API",
            description = "getUsrList method is HTTP GET mapping so to get data from database."
    )
    public ResponseEntity<Response> getUsrList(@RequestParam @Valid int page, @RequestParam @Valid int pageSize) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Users list fetched successfully!")
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
                            .build()
            );
        }
    }
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "add",
            summary = "To create new User from database, Call this API",
            description = "createUsr method is HTTP POST mapping so to store data in database."
    )
    public ResponseEntity<Response> createUsr(@RequestBody @Valid UsrDto usrDto) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User Added successfully!")
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
                            .build()
            );
        }
    }
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "update",
            summary = "To update existing User from database, Call this API",
            description = "updateUsr method is HTTP PUT mapping so to update data in database."
    )
    public ResponseEntity<Response> updateUsr(@RequestBody @Valid UsrDto usrDto, @PathVariable("id") @Valid Long id)  {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User updated successfully!")
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
                            .build()
            );
        }
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "delete",
            summary = "To delete existing User from database, Call this API",
            description = "deleteUser method is HTTP DELETE mapping so to delete data in database."
    )
    public ResponseEntity<Response> deleteUser(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User object deleted successfully!")
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
                            .build()
            );
        }
    }
}