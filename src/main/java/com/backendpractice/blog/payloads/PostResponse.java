package com.backendpractice.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

  private List<PostDto> content;
  private int pageSize;
  private int pageNumber;
  private long totalElements;
  private int totalPages;
  private boolean lastPage;
}
