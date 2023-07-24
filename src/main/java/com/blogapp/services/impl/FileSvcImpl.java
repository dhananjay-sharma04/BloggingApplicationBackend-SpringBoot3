package com.blogapp.services.impl;

import com.blogapp.services.FileSvc;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileSvcImpl implements FileSvc {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

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
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {

        String fullPath = path+File.separator+filename;

        //db logic to return input stream

        return new FileInputStream(fullPath);
    }
}