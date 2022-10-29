package com.backendpractice.blog.services;

import com.backendpractice.blog.payloads.ProfileDto;
import java.util.List;

public interface ProfileService {

  ProfileDto registerNewProfile(ProfileDto profile);

  ProfileDto createProfile(ProfileDto userEntity);

  ProfileDto updateProfile(ProfileDto userEntity, Long userId);

  ProfileDto getProfileById(Long userId);

  List<ProfileDto> getAllProfiles();

  void deleteProfileById(Long userId);
}
