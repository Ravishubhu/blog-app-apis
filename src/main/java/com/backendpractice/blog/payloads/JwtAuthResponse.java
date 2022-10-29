package com.backendpractice.blog.payloads;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponse {

  private String token;
}
