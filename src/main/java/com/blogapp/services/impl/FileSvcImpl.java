package com.blogapp.services.impl;

import com.blogapp.services.FileSvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileSvcImpl implements FileSvc {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        return null;
    }
}