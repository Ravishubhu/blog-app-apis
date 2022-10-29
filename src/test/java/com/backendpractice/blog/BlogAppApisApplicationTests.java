package com.backendpractice.blog;

import com.backendpractice.blog.repositories.ProfileRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApisApplicationTests {

  @Autowired private ProfileRepo profileRepo;

  @Test
  void contextLoads() {}

  @Test
  void repoTest() {
    String className = this.profileRepo.getClass().getName();
    String packageName = this.profileRepo.getClass().getPackageName();
    System.out.println(className + " ||||| " + packageName);
  }
}
