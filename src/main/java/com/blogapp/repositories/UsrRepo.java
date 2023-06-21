package com.blogapp.repositories;

import com.blogapp.models.Usr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsrRepo extends JpaRepository<Usr, Long> {
    Optional<Usr> findByEmail(String email);
}