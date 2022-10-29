package com.backendpractice.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

  private final String resourceName;
  private final String fieldName;
  private final Long fieldValue;

  public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
    super(
        String.format(
            "%s mil hi nahi raha hai jo manng rha hai %s : %s",
            resourceName, fieldName, fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
    super(
        String.format(
            "%s mil hi nahi raha hai jo manng rha hai %s : %s",
            resourceName, fieldName, fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = 0L;
  }
}
