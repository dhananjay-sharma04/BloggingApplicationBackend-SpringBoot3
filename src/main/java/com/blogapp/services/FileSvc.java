package com.blogapp.services;

import com.blogapp.exceptions.MediaTypeNotSupported;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Component
public interface FileSvc {
    String uploadImage(MultipartFile file) throws IOException, NullPointerException,MediaTypeNotSupported;
    InputStream getImage(String filename) throws FileNotFoundException;
}