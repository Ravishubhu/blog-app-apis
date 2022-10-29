package com.backendpractice.blog.repositories;

import com.backendpractice.blog.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<CommentEntity, Long> {

}
