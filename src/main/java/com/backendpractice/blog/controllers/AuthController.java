package com.backendpractice.blog.controllers;

import com.backendpractice.blog.payloads.JwtAuthRequest;
import com.backendpractice.blog.payloads.JwtAuthResponse;
import com.backendpractice.blog.payloads.ProfileDto;
import com.backendpractice.blog.security.JwtTokenHelper;
import com.backendpractice.blog.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  private JwtTokenHelper jwtTokenHelper;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ProfileService profileService;

  @PostMapping("/login")
  public ResponseEntity<JwtAuthResponse> createToken(
      @RequestBody JwtAuthRequest request
  ) throws Exception {

    this.authenticate(request.getUsername(), request.getPassword());

    UserDetails userDetail = this.userDetailsService.loadUserByUsername(request.getUsername());

    String token = this.jwtTokenHelper.generateToken(userDetail);

    return new ResponseEntity<>(JwtAuthResponse.builder().token(token).build(), HttpStatus.OK);
  }

  private void authenticate(String username, String passwordEncoder) throws Exception {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        username, passwordEncoder);
    try {
      this.authenticationManager.authenticate(authenticationToken);
    } catch (BadCredentialsException e) {
      System.out.println("Invalid details");
      throw new RuntimeException("Invalid Password");
    }
  }

  //register new user api
  @PostMapping("/register")
  public ResponseEntity<ProfileDto> registerUser(@RequestBody ProfileDto profileDto) {
    ProfileDto registeredNewProfile = this.profileService.registerNewProfile(profileDto);
    return new ResponseEntity<>(registeredNewProfile, HttpStatus.CREATED);
  }

}
