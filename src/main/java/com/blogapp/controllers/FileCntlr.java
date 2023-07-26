package com.blogapp.controllers;

import com.blogapp.exceptions.MediaTypeNotSupported;
import com.blogapp.response.Response;
import com.blogapp.services.impl.FileSvcImpl;
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
@RequestMapping(value = "/files")
@Tag(name = "File Controller", description = "Api related to operation on files")
public class FileCntlr {
    @Autowired
    private FileSvcImpl fileSvc;
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "uploadFile",
            summary = "To upload new File in application and store in file system, Call this API",
            description = "uploadFile method is HTTP POST mapping so to store file in file system."
    )
    public ResponseEntity<Response> uploadFile(@Valid @RequestBody MultipartFile image) throws NullPointerException, MediaTypeNotSupported, IOException {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("Image uploaded successfully!")
                            .data(Collections.singletonMap("ImageName", fileSvc.uploadImage(image)))
                            .build()
            );
        } catch (MediaTypeNotSupported mediaTypeNotSupported){
            return ResponseEntity.ok(
                    Response.builder()
                            .responseTime(LocalDateTime.now())
                            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                            .message(mediaTypeNotSupported.getMessage())
                            .build()
            );
        }
        catch (IOException | NullPointerException exception) {
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