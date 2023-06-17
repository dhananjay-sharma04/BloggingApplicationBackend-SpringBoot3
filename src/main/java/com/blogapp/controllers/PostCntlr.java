package com.blogapp.controllers;

import com.blogapp.dto.CatgDto;
import com.blogapp.dto.PostDto;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.response.Response;
import com.blogapp.services.impl.PostSvcImpl;
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
@RequestMapping(value = "/posts")
public class PostCntlr {
    @Autowired
    private PostSvcImpl postSvc;
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getPostList() {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Posts list fetched successfully!")
                            .method("PostCntlr.getPostList")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("posts", postSvc.getPostList()))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("PostCntlr.getPostList")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @GetMapping(value = "/getPostById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getPostById(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post fetched successfully!")
                            .method("PostCntlr.getPostById")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("post", postSvc.getPostById(id)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("PostCntlr.getPostById")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @GetMapping(value = "/getPostByUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getPostByUser(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post fetched successfully!")
                            .method("PostCntlr.getPostById")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("post", postSvc.getPostByUser(id)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("PostCntlr.getPostById")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @GetMapping(value = "/getPostByCategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getPostByCategory(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post fetched successfully!")
                            .method("PostCntlr.getPostById")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("post", postSvc.getPostByCategory(id)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("PostCntlr.getPostById")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @GetMapping(value = "/getPostByKeyword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> searchPost(@RequestParam @Valid String keyword) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Posts fetched successfully!")
                            .method("PostCntlr.searchPost")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("post", postSvc.searchPost(keyword)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("PostCntlr.searchPost")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @GetMapping(value = "/get_pageable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getPostList(@RequestParam @Valid int page, @RequestParam @Valid int pageSize) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post list fetched successfully!")
                            .method("PostCntlr.getPostList")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("posts", postSvc.getPostList(page, pageSize)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("PostCntlr.getPostList")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createPost(@RequestBody @Valid PostDto postDto, @RequestParam @Valid Long userId, @RequestParam @Valid Long catId) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post Added successfully!")
                            .method("PostCntlr.createPost")
                            .executionMessage("Implemented business logic of Simple Search class method")
                            .data(Collections.singletonMap("post", postSvc.createPost(postDto, userId, catId)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("PostCntlr.createPost")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updatePost(@RequestBody @Valid PostDto postDto, @PathVariable("id") @Valid Long id)  {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post updated successfully!")
                            .method("PostCntlr.updatePost")
                            .executionMessage("Implemented business logic of service class method")
                            .data(Collections.singletonMap("post", postSvc.updatePost(id, postDto)))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("PostCntlr.updatePost")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deletePost(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post object deleted successfully!")
                            .method("PostCntlr.deletePost")
                            .executionMessage("Implemented business logic of service class method")
                            .data(postSvc.delPost(id))
                            .build()
            );
        } catch (ResourceNotFound resourceNotFound) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(resourceNotFound.getMessage())
                            .method("PostCntlr.deletePost")
                            .executionMessage("Implemented business logic of service class method")
                            .build()
            );
        }
    }
}