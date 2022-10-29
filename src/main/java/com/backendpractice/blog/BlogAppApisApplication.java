package com.backendpractice.blog;

import com.backendpractice.blog.config.AppConstants;
import com.backendpractice.blog.entities.Role;
import com.backendpractice.blog.repositories.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleRepository roleRepository;

  public static void main(String[] args) {
    SpringApplication.run(BlogAppApisApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("This is the password --> ");
    System.out.println(this.passwordEncoder.encode("ultra.slim"));

    try {
      Role role = new Role();
      role.setId(AppConstants.ADMIN_USER);
      role.setName("ROLE_ADMIN");

      Role role1 = new Role();
      role1.setId(AppConstants.NORMAL_USER);
      role1.setName("ROLE_NORMAL");

      List<Role> roles = List.of(role, role1);

      var result = this.roleRepository.saveAll(roles);

      result.forEach(r -> System.out.println(r.getName()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
