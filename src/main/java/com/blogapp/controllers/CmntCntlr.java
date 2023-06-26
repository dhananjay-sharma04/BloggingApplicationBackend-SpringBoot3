package com.blogapp.controllers;

import com.blogapp.dto.CmntDto;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.response.Response;
import com.blogapp.services.impl.CmntSvcImpl;
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
@RequestMapping(value = "/comments")
@Tag(name = "Comment Controller", description = "Api related to fetch, create and delete comment on post")
public class CmntCntlr {
    @Autowired
    private CmntSvcImpl cmntSvc;
    @GetMapping(value = "/getByPostId/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "getByPostId",
            summary = "To fetch List of Comment on specific post from database, Call this API",
            description = "getCmntList method is HTTP GET mapping so to get data from database."
    )
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
    @Operation(
            operationId = "add",
            summary = "To comment on specific post, Call this API",
            description = "createCmnt method is HTTP POST mapping so to store data in database."
    )
    public ResponseEntity<Response> createCmnt(@RequestBody @Valid CmntDto cmntDto, @RequestParam @Valid Long userId, @RequestParam @Valid Long postId) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("comments Added successfully!")
                            .method("CmntCntlr.createCmnt")
                            .executionMessage("Implemented business logic of Simple Search class method")
                            .data(Collections.singletonMap("comment", cmntSvc.createCmnt(cmntDto, userId, postId)))
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
    @Operation(
            operationId = "delete",
            summary = "To delete commented comment on specific post from database, Call this API",
            description = "deleteUser method is HTTP DELETE mapping so to delete data in database."
    )
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