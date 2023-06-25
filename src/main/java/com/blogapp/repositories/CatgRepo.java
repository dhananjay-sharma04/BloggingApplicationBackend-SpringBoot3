package com.blogapp.repositories;

import com.blogapp.models.Catg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatgRepo extends JpaRepository<Catg,Long> {
    Catg findByCatgTitle(String title);
}