package com.backendpractice.blog.services;

import com.backendpractice.blog.payloads.PostDto;
import com.backendpractice.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Long categoryId, Long profileId);

    PostDto updatePost(PostDto postDto, Long postId);

    void deletePost(Long postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long postId);

    List<PostDto> getPostByCategory(Long categoryId);

    List<PostDto> getPostByProfile(Long profileId);

    List<PostDto> searchPost(String keyword);
}
