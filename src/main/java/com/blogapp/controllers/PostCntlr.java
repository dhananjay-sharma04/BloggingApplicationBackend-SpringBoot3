package com.blogapp.controllers;

import com.blogapp.exceptions.MediaTypeNotSupported;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.response.Response;
import com.blogapp.services.impl.FileSvcImpl;
import com.blogapp.services.impl.PostSvcImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping(value = "/posts")
@Tag(name = "Post Controller", description = "Api related to CRUD operation on posts")
public class PostCntlr {
    @Autowired
    private PostSvcImpl postSvc;
    @Autowired
    private FileSvcImpl fileSvc;
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "get",
            summary = "To fetch List of Posts without pagination or any args, Call this API",
            description = "getPostList method is HTTP GET mapping so to get data from database."
    )
    public ResponseEntity<Response> getPostList() {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Posts list fetched successfully!")
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
                            .build()
            );
        }
    }
    @GetMapping(value = "/getPostById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "getPostById",
            summary = "To fetch single post by id from database, Call this API",
            description = "getPostById method is HTTP GET mapping so to get data from database."
    )
    public ResponseEntity<Response> getPostById(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post fetched successfully!")
                            .data(Collections.singletonMap("posts", postSvc.getPostById(id)))
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
    @GetMapping(value = "/getPostByUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "getPostByUser",
            summary = "To fetch List posts by user from database, Call this API",
            description = "getPostByUser method is HTTP GET mapping so to get data from database."
    )
    public ResponseEntity<Response> getPostByUser(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post fetched successfully!")
                            .data(Collections.singletonMap("posts", postSvc.getPostByUser(id)))
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
    @GetMapping(value = "/getPostByCategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "getPostByCategory",
            summary = "To fetch List posts by Category id from database, Call this API",
            description = "getPostByCategory method is HTTP GET mapping so to get data from database."
    )
    public ResponseEntity<Response> getPostByCategory(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post fetched successfully!")
                            .data(Collections.singletonMap("posts", postSvc.getPostByCategory(id)))
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
    @GetMapping(value = "/getPostByKeyword", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "getPostByKeyword",
            summary = "To search list of posts containing keyword in title from database, Call this API",
            description = "searchPost method is HTTP GET mapping so to get data from database."
    )
    public ResponseEntity<Response> searchPost(@RequestParam @Valid String keyword) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Posts fetched successfully!")
                            .data(Collections.singletonMap("posts", postSvc.searchPost(keyword)))
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
    @GetMapping(value = "/get_pageable", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "get_pageable",
            summary = "To fetch list of posts with pagination from database, Call this API",
            description = "searchPost method is HTTP GET mapping so to get data from database."
    )
    public ResponseEntity<Response> getPostList(@RequestParam @Valid int page, @RequestParam @Valid int pageSize) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post list fetched successfully!")
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
                            .build()
            );
        }
    }
    @PostMapping(value = "/add/{userId}/{catTitle}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "add",
            summary = "To publish new post in application and store in database, Call this API",
            description = "createPost method is HTTP POST mapping so to store data from database."
    )
    public ResponseEntity<Response> createPost(@Valid @PathVariable("userId") String userId,
                                               @Valid @PathVariable("catTitle") String catTitle,
                                               @Valid @RequestPart String postDto,
                                               @Valid @RequestPart(required = false) MultipartFile image) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post Added successfully!")
                            .data(Collections.singletonMap("posts", postSvc.createPost(postDto, image,Long.valueOf(userId), catTitle)))
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
        } catch (MediaTypeNotSupported mediaTypeNotSupported) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                            .message(mediaTypeNotSupported.getMessage())
                            .build()
            );
        } catch (IOException ioException){
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(ioException.getMessage())
                            .build()
            );
        }
    }
    @PutMapping(value = "/update/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "update",
            summary = "To update published Post in database, Call this API",
            description = "updatePost method is HTTP PUT mapping so to update data in database."
    )
    public ResponseEntity<Response> updatePost(@RequestPart @Valid String postDto,
                                               @RequestPart(required = false) @Valid MultipartFile image,
                                               @RequestPart @Valid String isDeleteImage,
                                               @PathVariable("postId") @Valid Long id,
                                               @RequestPart(required = false) String catTitle)  {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post updated successfully!")
                            .data(Collections.singletonMap("posts", postSvc.updatePost(id, postDto, image, catTitle, isDeleteImage)))
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
        } catch (MediaTypeNotSupported mediaTypeNotSupported) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                            .message(mediaTypeNotSupported.getMessage())
                            .build()
            );
        } catch (IOException ioException){
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(ioException.getMessage())
                            .build()
            );
        }
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "delete",
            summary = "To delete published post from database, Call this API",
            description = "deletePost method is HTTP DELETE mapping so to delete data in database."
    )
    public ResponseEntity<Response> deletePost(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Post object deleted successfully!")
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
                            .build()
            );
        } catch (IOException ioException){
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(ioException.getMessage())
                            .build()
            );
        }
    }
    @GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(
            operationId = "image",
            summary = "To download existing image form file system, Call this API",
            description = "downloadImage method is HTTP GET mapping so to fetch file from file system."
    )
    public void downloadImage(@PathVariable("name") String imageName,
                              HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        InputStream inputStream = fileSvc.getImage(imageName);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }
}