package com.blogapp.services.impl;

import com.blogapp.exceptions.MediaTypeNotSupported;
import com.blogapp.services.FileSvc;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileSvcImpl implements FileSvc {
    @Value("${project.image}")
    private String path;
    @Override
    public String uploadImage(MultipartFile file) throws MediaTypeNotSupported,IOException, NullPointerException {
        if (file == null){
            throw new NullPointerException("File can't be empty");
        } else if (Objects.equals(file.getContentType(), MediaType.IMAGE_JPEG_VALUE) || Objects.equals(file.getContentType(), MediaType.IMAGE_PNG_VALUE)){
            String name = file.getOriginalFilename();
            //random name generate file
            String randomID = UUID.randomUUID().toString();
            String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
            //full path
            String filePath = path + File.separator + fileName;
            //create folder if not created
            File f = new File(path);
            if (!f.exists()){
                f.mkdir();
            }
            //file copy
            Files.copy(file.getInputStream(), Paths.get(filePath));
            return fileName;
        } else {
            throw new MediaTypeNotSupported("MediaType should be JPEG or PNG");
        }
    }

    @Override
    public InputStream getImage(String filename) throws FileNotFoundException {
        String fullPath = path+File.separator+filename;
        InputStream inputStream;
        try {
            inputStream= new FileInputStream(fullPath);
            return inputStream;
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException();
        }
    }
}