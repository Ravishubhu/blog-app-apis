package com.backendpractice.blog.security;

import com.backendpractice.blog.exceptions.ResourceNotFoundException;
import com.backendpractice.blog.repositories.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  private ProfileRepo profileRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //loading user from database by username

    return this.profileRepo.findByEmail(username)
        .orElseThrow(() -> new ResourceNotFoundException("Profile", "Email", username));
  }
}
