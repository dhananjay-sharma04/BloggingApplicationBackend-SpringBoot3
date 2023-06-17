package com.blogapp.repositories;

import com.blogapp.models.Usr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsrRepo extends JpaRepository<Usr, Long> {
}