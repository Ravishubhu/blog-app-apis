package com.backendpractice.blog.repositories;

import com.backendpractice.blog.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {}
