package com.blogapp.services;

import com.blogapp.dto.PostDto;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
    Post createPost(PostDto postDto, Long usrId, Long catId) throws ResourceNotFound;
    Post updatePost(Long id, PostDto postDto) throws ResourceNotFound;
    Map<String, Boolean> delPost(Long id) throws ResourceNotFound;
}