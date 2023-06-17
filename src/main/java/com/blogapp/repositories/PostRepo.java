package com.blogapp.repositories;

import com.blogapp.models.Catg;
import com.blogapp.models.Post;
import com.blogapp.models.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByUser(Usr usr);
    List<Post> findByCategory(Catg catg);
    List<Post> findByTitleContaining(String title);
}