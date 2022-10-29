package com.backendpractice.blog.services.impl;

import com.backendpractice.blog.entities.CommentEntity;
import com.backendpractice.blog.exceptions.ResourceNotFoundException;
import com.backendpractice.blog.payloads.CommentDto;
import com.backendpractice.blog.repositories.CommentRepo;
import com.backendpractice.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private PostServiceImpl postService;
  @Autowired
  private CommentRepo commentRepo;
  @Autowired
  private ModelMapper mapper;

  @Override
  public CommentDto createComment(CommentDto comment, Long postId) {
    var post = this.postService.getPostEntityById(postId);
    CommentEntity commentEntity = this.mapper.map(comment, CommentEntity.class);
    commentEntity.setPost(post);
    CommentEntity savedComment = this.commentRepo.save(commentEntity);
    return this.mapper.map(savedComment, CommentDto.class);
  }

  @Override
  public void deleteComment(Long commentId) {
    var toBeDeleted = getById(commentId);
    commentRepo.delete(toBeDeleted);
  }

  public CommentEntity getById(Long commentId) {
    return commentRepo.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
  }
}
