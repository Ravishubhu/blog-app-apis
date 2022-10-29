package com.backendpractice.blog.services;

import com.backendpractice.blog.payloads.CategoryDto;
import com.backendpractice.blog.payloads.CategoryResponse;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    void deleteCategory(Long categoryId);

    CategoryDto getCategory(Long categoryId);

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);

}
