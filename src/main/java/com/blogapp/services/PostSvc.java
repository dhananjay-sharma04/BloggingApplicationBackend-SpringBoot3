package com.blogapp.services;

import com.blogapp.dto.PostDto;
import com.blogapp.exceptions.MediaTypeNotSupported;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface PostSvc {
    Post getPostById(Long id) throws ResourceNotFound;
    List<Post> getPostList() throws ResourceNotFound;
    Page<Post> getPostList(int page, int pageSize) throws ResourceNotFound;
    List<Post> getPostByCategory(Long catId) throws ResourceNotFound;
    List<Post> getPostByUser(Long usrId) throws ResourceNotFound;
    List<Post> searchPost(String keyword) throws ResourceNotFound;
    Post createPost(String postDto, MultipartFile image, Long usrId, String catTitle) throws ResourceNotFound, IOException, MediaTypeNotSupported;
    Post updatePost(Long id, String postDto, MultipartFile image, String catTitle, String isDeleteImage) throws ResourceNotFound, MediaTypeNotSupported, IOException;
    Map<String, Boolean> delPost(Long id) throws ResourceNotFound, IOException;
}