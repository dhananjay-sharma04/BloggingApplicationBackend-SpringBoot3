package com.blogapp.controllers;

import com.blogapp.dto.CmntDto;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.response.Response;
import com.blogapp.services.impl.CmntSvcImpl;
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
@RequestMapping(value = "/comments")
public class CmntCntlr {
    @Autowired
    private CmntSvcImpl cmntSvc;
    @GetMapping(value = "/getByPostId/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getCmntList(@PathVariable("postId") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Comments list fetched successfully!")
                            .method("CmntCntlr.getCmntList")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("comments", cmntSvc.getCmntByPost(id)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("CmntCntlr.getCmntList")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createCmnt(@RequestBody @Valid CmntDto cmntDto) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("comments Added successfully!")
                            .method("CmntCntlr.createCmnt")
                            .executionMessage("Implemented business logic of Simple Search class method")
                            .data(Collections.singletonMap("comment", cmntSvc.createCmnt(cmntDto)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("CmntCntlr.createCmnt")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteCmnt(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("comments object deleted successfully!")
                            .method("CmntCntlr.deleteCmnt")
                            .executionMessage("Implemented business logic of service class method")
                            .data(cmntSvc.delCmnt(id))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("CmntCntlr.deleteCmnt")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
}