package com.backendpractice.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

  private Long id;
  private String title;
  private String content;
  private String imageName;
  private Date addedDate;
  private CategoryDto category;
  private ProfileDto profile;
  private Set<CommentDto> comments = new HashSet<>();
}
