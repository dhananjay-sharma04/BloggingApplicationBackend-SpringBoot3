package com.blogapp.repositories;

import com.blogapp.models.Catg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatgRepo extends JpaRepository<Catg,Long> {
}