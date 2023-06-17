package com.blogapp.services;

import com.blogapp.dto.CmntDto;
import com.blogapp.dto.PostDto;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Cmnt;
import com.blogapp.models.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CmntSvc {
    List<Cmnt> getCmntByPost(Long postId) throws ResourceNotFound;
    Cmnt createCmnt(CmntDto cmntDto) throws ResourceNotFound;
    Map<String, Boolean> delCmnt(Long id) throws ResourceNotFound;
}