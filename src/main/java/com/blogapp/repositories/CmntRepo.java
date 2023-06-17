package com.blogapp.repositories;

import com.blogapp.models.Cmnt;
import com.blogapp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmntRepo extends JpaRepository<Cmnt, Long> {
    List<Cmnt> findByPost(Post post);
}