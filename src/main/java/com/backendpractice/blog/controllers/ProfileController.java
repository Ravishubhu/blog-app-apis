package com.backendpractice.blog.controllers;

import com.backendpractice.blog.payloads.ApiResponse;
import com.backendpractice.blog.payloads.ProfileDto;
import com.backendpractice.blog.services.ProfileService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ProfileController {

  @Autowired
  private ProfileService profileService;

  // POST mapping create a new User profile
  @PostMapping("/")
  public ResponseEntity<ProfileDto> createUser(@Valid @RequestBody ProfileDto profileDto) {
    var createUserDto = this.profileService.createProfile(profileDto);
    return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
  }

  // PUT mapping update an old User profile
  @PutMapping("/{userId}")
  public ResponseEntity<ProfileDto> updateUser(
      @Valid @RequestBody ProfileDto profileDto, @PathVariable("userId") Long uId) {
    var createUserDto = this.profileService.updateProfile(profileDto, uId);
    return ResponseEntity.ok(createUserDto);
  }

  // delete mapping delete an User profile
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long uId) {
    this.profileService.deleteProfileById(uId);
    return new ResponseEntity<>(
        new ApiResponse("User with id " + uId + " is deleted", true), HttpStatus.OK);
    //    return ResponseEntity.ok(Map.of("Message", "User with id " + uId + " is deleted"));
    //    return new ResponseEntity<>(
    //        Map.of("Message", "User with id " + uId + " is deleted"), HttpStatus.OK);

  }

  // Get mapping get user
  @GetMapping("/")
  public ResponseEntity<List<ProfileDto>> getAllUsers() {
    return ResponseEntity.ok(profileService.getAllProfiles());
  }

  @GetMapping("/{userId}")
  public ResponseEntity<ProfileDto> getUserById(@PathVariable("userId") Long uId) {
    return ResponseEntity.ok(profileService.getProfileById(uId));
  }
}
