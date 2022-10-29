package com.backendpractice.blog.services.impl;

import com.backendpractice.blog.entities.PostEntity;
import com.backendpractice.blog.exceptions.ResourceNotFoundException;
import com.backendpractice.blog.payloads.PostDto;
import com.backendpractice.blog.payloads.PostResponse;
import com.backendpractice.blog.repositories.PostRepo;
import com.backendpractice.blog.services.PostService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private PostRepo postRepo;

  @Autowired
  private ProfileServiceImpl profileService;

  @Autowired
  private CategoryServiceImpl categoryService;
  @Autowired
  private ModelMapper mapper;

  @Override
  public PostDto createPost(PostDto postDto, Long categoryId, Long profileId) {

    var profileEntity = this.profileService.getProfileEntity(profileId);

    var categoryEntity = this.categoryService.getCategoryEntity(categoryId);

    var postEntity = this.mapper.map(postDto, PostEntity.class);
    postEntity.setImageName("default.png");
    postEntity.setAddedDate(new Date());
    postEntity.setProfile(profileEntity);
    postEntity.setCategory(categoryEntity);

    return this.mapper.map(this.postRepo.save(postEntity), PostDto.class);
  }

  @Override
  public PostDto updatePost(PostDto postDto, Long postId) {
    var toBeUpdatedPost = this.getPostEntityById(postId);
    Optional.ofNullable(postDto.getTitle()).ifPresent(toBeUpdatedPost::setTitle);
    Optional.ofNullable(postDto.getContent()).ifPresent(toBeUpdatedPost::setContent);
    Optional.ofNullable(postDto.getImageName()).ifPresent(toBeUpdatedPost::setImageName);
    return this.mapper.map(this.postRepo.save(toBeUpdatedPost), PostDto.class);
  }

  @Override
  public void deletePost(Long postId) {
    var post = getPostEntityById(postId);
    postRepo.delete(post);
  }

  @Override
  public PostResponse getAllPost(
      Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

    var sort = Sort.by(sortBy);
    if (!sortDir.equalsIgnoreCase("asc")) {
      sort = sort.descending();
    } else {
      sort = sort.ascending();
    }

    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    var pagePost = this.postRepo.findAll(pageable);
    var allPosts = pagePost.getContent();
    var postDto = allPosts.stream().map(e -> mapper.map(e, PostDto.class)).toList();

    return PostResponse.builder()
        .content(postDto)
        .pageNumber(pagePost.getNumber())
        .pageSize(pagePost.getSize())
        .totalElements(pagePost.getTotalElements())
        .totalPages(pagePost.getTotalPages())
        .lastPage(pagePost.isLast())
        .build();
  }

  @Override
  public PostDto getPostById(Long postId) {
    return mapper.map(getPostEntityById(postId), PostDto.class);
  }

  @Override
  public List<PostDto> getPostByCategory(Long categoryId) {
    var category = this.categoryService.getCategoryEntity(categoryId);
    return this.postRepo.findByCategory(category).stream()
        .map(e -> mapper.map(e, PostDto.class))
        .toList();
  }

  @Override
  public List<PostDto> getPostByProfile(Long profileId) {
    return this.postRepo.findByProfile(this.profileService.getProfileEntity(profileId)).stream()
        .map(e -> mapper.map(e, PostDto.class))
        .toList();
  }

  @Override
  public List<PostDto> searchPost(String keyword) {
    List<PostEntity> titleContaining = this.postRepo.findByTitleContaining(keyword);
    return titleContaining.stream()
        .map(e -> mapper.map(e, PostDto.class))
        .toList();
  }

  public PostEntity getPostEntityById(Long postId) {

    return this.postRepo
        .findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
  }
}
