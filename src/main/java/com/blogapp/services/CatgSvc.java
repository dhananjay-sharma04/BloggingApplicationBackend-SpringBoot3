package com.blogapp.services;

import com.blogapp.dto.CatgDto;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Catg;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CatgSvc {
    List<Catg> getCatgList() throws ResourceNotFound;
    Catg getCatgById(Long id) throws ResourceNotFound;
    Catg createCatg(CatgDto catgDto) throws ResourceAlreadyExists;
    Catg updateCatg(Long id, CatgDto catgDto) throws ResourceNotFound, ResourceAlreadyExists;
    Map<String, Boolean> delCatg(Long id) throws ResourceNotFound;
}