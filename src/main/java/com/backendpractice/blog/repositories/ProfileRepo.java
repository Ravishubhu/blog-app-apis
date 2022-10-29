package com.backendpractice.blog.repositories;

import com.backendpractice.blog.entities.ProfileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepo extends JpaRepository<ProfileEntity, Long> {

  @Query("select p from ProfileEntity p where p.email = ?1")
  Optional<ProfileEntity> findByEmail(String email);

}
