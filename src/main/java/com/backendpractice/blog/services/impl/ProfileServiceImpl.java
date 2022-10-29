package com.backendpractice.blog.services.impl;


import com.backendpractice.blog.config.AppConstants;
import com.backendpractice.blog.entities.ProfileEntity;
import com.backendpractice.blog.entities.Role;
import com.backendpractice.blog.exceptions.ResourceNotFoundException;
import com.backendpractice.blog.payloads.ProfileDto;
import com.backendpractice.blog.repositories.ProfileRepo;
import com.backendpractice.blog.repositories.RoleRepository;
import com.backendpractice.blog.services.ProfileService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

  @Autowired
  private ProfileRepo profileRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public ProfileDto registerNewProfile(ProfileDto profile) {

    ProfileEntity entity = this.modelMapper.map(profile, ProfileEntity.class);

    // encoded the password
    entity.setPassword(passwordEncoder.encode(profile.getPassword()));

    //roles
    Role normalUser = this.roleRepository.findById(AppConstants.NORMAL_USER).orElse(null);
    entity.getRoles().add(normalUser);

    ProfileEntity newProfile = this.profileRepo.save(entity);

    return this.modelMapper.map(newProfile, ProfileDto.class);
  }

  @Override
  public ProfileDto createProfile(ProfileDto profileDto) {
    var profile = dtoToEntity(profileDto);
    var saved = profileRepo.save(profile);
    return entityToDto(saved);
  }

  @Override
  public ProfileDto updateProfile(ProfileDto profileDto, Long userId) {
    var user = this.getProfileEntity(userId);
    user.setName(profileDto.getName());
    user.setEmail(profileDto.getEmail());
    user.setPassword(profileDto.getPassword());
    user.setAbout(profileDto.getAbout());
    return this.entityToDto(this.profileRepo.save(user));
  }

  @Override
  public ProfileDto getProfileById(Long userId) {
    return this.entityToDto(this.getProfileEntity(userId));
  }

  @Override
  public List<ProfileDto> getAllProfiles() {
    var users = this.profileRepo.findAll();
    return users.stream().map(this::entityToDto).toList();
  }

  @Override
  public void deleteProfileById(Long userId) {
    var user = this.getProfileEntity(userId);
    this.profileRepo.delete(user);
  }

  public ProfileEntity getProfileEntity(Long userId) {
    return this.profileRepo
        .findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
  }

  private ProfileEntity dtoToEntity(ProfileDto profileDto) {
    return this.modelMapper.map(profileDto, ProfileEntity.class);
  }

  private ProfileDto entityToDto(ProfileEntity profileEntity) {
    return this.modelMapper.map(profileEntity, ProfileDto.class);
  }
}
