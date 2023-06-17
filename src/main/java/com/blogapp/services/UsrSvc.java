package com.blogapp.services;

import com.blogapp.dto.UsrDto;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Usr;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UsrSvc {
    Page<Usr> getUsrList(int page, int pageSize) throws ResourceNotFound;
    Usr createUsr(UsrDto usrDto) throws ResourceAlreadyExists;
    Usr updateUsr(Long id, UsrDto usrDto) throws ResourceNotFound, ResourceAlreadyExists;
    Map<String, Boolean> delUsr(Long id) throws ResourceNotFound;
}