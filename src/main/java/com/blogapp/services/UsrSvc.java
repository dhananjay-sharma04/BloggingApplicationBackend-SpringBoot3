package com.blogapp.services;

import com.blogapp.dto.UsrDto;
import com.blogapp.exceptions.MediaTypeNotSupported;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Usr;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public interface UsrSvc {
    Page<Usr> getUsrList(int page, int pageSize) throws ResourceNotFound;
    Usr createUsr(String usrDto, MultipartFile image) throws ResourceAlreadyExists, IOException, MediaTypeNotSupported;
    Usr updateUsr(Long id, String usrD, MultipartFile image, String isDeleteImage) throws ResourceNotFound, ResourceAlreadyExists, IOException, MediaTypeNotSupported;
    Map<String, Boolean> delUsr(Long id) throws ResourceNotFound, IOException;
}