package com.blogapp.controllers;

import com.blogapp.dto.UsrDto;
import com.blogapp.exceptions.MediaTypeNotSupported;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.response.Response;
import com.blogapp.services.impl.FileSvcImpl;
import com.blogapp.services.impl.UsrSvcImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@RequestMapping(value = "/users")
@Tag(name = "User Controller", description = "Api related to fetch, create, update and delete user")
public class UsrCntlr {
    @Autowired
    private UsrSvcImpl usrSvc;
    @Autowired
    private FileSvcImpl fileSvc;
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
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "add",
            summary = "To create new User from database, Call this API",
            description = "createUsr method is HTTP POST mapping so to store data in database."
    )
    public ResponseEntity<Response> createUsr(@RequestPart @Valid String usrDto,
                                              @RequestPart(required = false) @Valid MultipartFile image) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User Added successfully!")
                            .data(Collections.singletonMap("users", usrSvc.createUsr(usrDto, image)))
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
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "update",
            summary = "To update existing User from database, Call this API",
            description = "updateUsr method is HTTP PUT mapping so to update data in database."
    )
    public ResponseEntity<Response> updateUsr(@RequestPart @Valid String usrDto,
                                              @RequestPart(required = false) @Valid MultipartFile image,
                                              @RequestPart @Valid String isDeleteImage,
                                              @PathVariable("id") @Valid Long id)  {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("User updated successfully!")
                            .data(Collections.singletonMap("users", usrSvc.updateUsr(id, usrDto, image, isDeleteImage)))
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