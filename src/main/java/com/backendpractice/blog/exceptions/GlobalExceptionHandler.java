package com.backendpractice.blog.exceptions;

import com.backendpractice.blog.payloads.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(
      ResourceNotFoundException ex) {
    var apiResponse = new ApiResponse(ex.getMessage(), false);
    return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(
      MethodArgumentNotValidException ex) {
    Map<String, String> res = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              var fieldName = ((FieldError) error).getField();
              var msg = error.getDefaultMessage();
              res.put(fieldName, msg);
            });

    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
  }
}
