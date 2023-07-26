package com.blogapp.controllers;

import com.blogapp.dto.CatgDto;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.response.Response;
import com.blogapp.services.impl.CatgSvcImpl;
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
@RequestMapping(value = "/categories")
@Tag(name = "Category Controller", description = "Api related to fetch, create, update and delete categories")
public class CatgCntlr {
    @Autowired
    private CatgSvcImpl catgSvc;
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "get",
            summary = "To fetch List of Categories from database, Call this API",
            description = "getCatgList method is HTTP GET mapping so to get data from database."
    )
    public ResponseEntity<Response> getCatgList() {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Categories list fetched successfully!")
                            .data(Collections.singletonMap("categories", catgSvc.getCatgList()))
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
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "getCatgById",
            summary = "To fetch single category by id from database, Call this API",
            description = "getCatgById method is HTTP GET mapping so to get data from database."
    )
    public ResponseEntity<Response> getCatgById(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Category fetched successfully!")
                            .data(Collections.singletonMap("categories", catgSvc.getCatgById(id)))
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
            summary = "To create new Category in database, Call this API",
            description = "createCatg method is HTTP POST mapping so to store data in database."
    )
    public ResponseEntity<Response> createCatg(@RequestBody @Valid CatgDto catgDto) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Category Added successfully!")
                            .data(Collections.singletonMap("categories", catgSvc.createCatg(catgDto)))
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
            summary = "To update existing Category from database, Call this API",
            description = "updateCatg method is HTTP PUT mapping so to update data in database."
    )
    public ResponseEntity<Response> updateCatg(@RequestBody @Valid CatgDto catgDto, @PathVariable("id") @Valid Long id)  {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Category updated successfully!")
                            .data(Collections.singletonMap("categories", catgSvc.updateCatg(id, catgDto)))
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
            summary = "To delete existing Category from database, Call this API",
            description = "deleteCatg method is HTTP DELETE mapping so to delete data in database."
    )
    public ResponseEntity<Response> deleteCatg(@PathVariable("id") @Valid Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Category object deleted successfully!")
                            .data(catgSvc.delCatg(id))
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