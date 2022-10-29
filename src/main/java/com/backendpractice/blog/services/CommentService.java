package com.backendpractice.blog.services;

import com.backendpractice.blog.payloads.CommentDto;

public interface CommentService {

  CommentDto createComment(CommentDto comment, Long postId);

  void deleteComment(Long commentId);
}
