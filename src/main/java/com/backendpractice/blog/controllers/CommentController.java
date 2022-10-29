package com.backendpractice.blog.controllers;

import com.backendpractice.blog.payloads.ApiResponse;
import com.backendpractice.blog.payloads.CommentDto;
import com.backendpractice.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping("/post/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentEntity,
      @PathVariable("postId") Long postId) {
    CommentDto comment = commentService.createComment(commentEntity, postId);
    return new ResponseEntity<>(comment, HttpStatus.CREATED);
  }

  @PostMapping("/comments/(commentId)")
  public ResponseEntity<ApiResponse> deleteComment(
      @PathVariable("commentId") Long commentId) {
    commentService.deleteComment(commentId);
    return new ResponseEntity<>(new ApiResponse("Comment is deleted Successfully", true),
        HttpStatus.OK);
  }
}
