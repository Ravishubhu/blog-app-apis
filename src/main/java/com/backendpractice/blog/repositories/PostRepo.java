package com.backendpractice.blog.repositories;

import com.backendpractice.blog.entities.CategoryEntity;
import com.backendpractice.blog.entities.PostEntity;
import com.backendpractice.blog.entities.ProfileEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepo extends JpaRepository<PostEntity, Long> {

  @Query("select p from PostEntity p where p.profile = ?1")
  List<PostEntity> findByProfile(ProfileEntity profile);

  @Query("select p from PostEntity p where p.category = ?1")
  List<PostEntity> findByCategory(CategoryEntity category);

  @Query("select p from PostEntity p where p.title like concat('%', ?1, '%')")
  List<PostEntity> findByTitleContaining(String title);
}
