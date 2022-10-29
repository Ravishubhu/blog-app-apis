package com.backendpractice.blog.controllers;

import com.backendpractice.blog.config.AppConstants;
import com.backendpractice.blog.payloads.ApiResponse;
import com.backendpractice.blog.payloads.PostDto;
import com.backendpractice.blog.payloads.PostResponse;
import com.backendpractice.blog.services.FileService;
import com.backendpractice.blog.services.PostService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class PostController {

  @Autowired
  private PostService postService;
  @Autowired
  private FileService fileService;

  @Value("${project.image}")
  private String path;

  // create
  @PostMapping("/user/{userId}/category/{categoryId}/posts")
  public ResponseEntity<PostDto> createPost(
      @RequestBody PostDto postDto,
      @PathVariable("userId") Long profileId,
      @PathVariable("categoryId") Long categoryId) {
    return new ResponseEntity<>(
        this.postService.createPost(postDto, categoryId, profileId), HttpStatus.CREATED);
  }

  @GetMapping("/user/{userId}/posts")
  public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Long profileId) {
    var posts = postService.getPostByProfile(profileId);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @GetMapping("/category/{categoryId}/posts")
  public ResponseEntity<List<PostDto>> getPostsByCategory(
      @PathVariable("categoryId") Long categoryId) {
    var posts = postService.getPostByCategory(categoryId);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @GetMapping("/posts/{postId}")
  public ResponseEntity<PostDto> getPostsById(@PathVariable("postId") Long postId) {
    var posts = postService.getPostById(postId);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @GetMapping("/posts")
  public ResponseEntity<PostResponse> getAllPost(
      @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
      Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
      Integer pageSize,
      @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false)
      String sortBy,
      @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)
      String sortDir) {
    var postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(postResponse, HttpStatus.OK);
  }

  @DeleteMapping("/posts/{postId}")
  public ResponseEntity<ApiResponse> deletePostById(@PathVariable("postId") Long postId) {
    postService.deletePost(postId);
    return new ResponseEntity<>(
        new ApiResponse("Post is been Deleted Successfully", true), HttpStatus.ACCEPTED);
  }

  @PutMapping("/posts/{postId}")
  public ResponseEntity<PostDto> updatePost(
      @RequestBody PostDto postDto, @PathVariable("postId") Long postId) {
    PostDto updatePost = this.postService.updatePost(postDto, postId);
    return new ResponseEntity<>(updatePost, HttpStatus.ACCEPTED);
  }

  @GetMapping("/posts/search/{keyword}")
  public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keywords) {
    List<PostDto> posts = this.postService.searchPost(keywords);
    return new ResponseEntity<>(posts, HttpStatus.ACCEPTED);
  }

  // Post image upload

  @PostMapping("/post/image/upload/{postId}")
  public ResponseEntity<PostDto> uploadPostImage(
      @RequestParam("image") MultipartFile image,
      @PathVariable("postId") Long postId) throws IOException {

    PostDto postDto = this.postService.getPostById(postId);
    String uploadImage = this.fileService.uploadImage(path, image);
    postDto.setImageName(uploadImage);
    PostDto updatePost = this.postService.updatePost(postDto, postId);
    return new ResponseEntity<>(updatePost, HttpStatus.OK);
  }

  // Serve Image from Database
  @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
  public void downloadImage(
      @PathVariable("imageName") String imageName,
      HttpServletResponse response
  ) throws IOException {
    var resource = this.fileService.serveFile(path, imageName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource, response.getOutputStream());

  }
}
