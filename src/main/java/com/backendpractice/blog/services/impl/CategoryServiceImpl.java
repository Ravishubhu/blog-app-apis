package com.backendpractice.blog.services.impl;

import com.backendpractice.blog.entities.CategoryEntity;
import com.backendpractice.blog.exceptions.ResourceNotFoundException;
import com.backendpractice.blog.payloads.CategoryDto;
import com.backendpractice.blog.payloads.CategoryResponse;
import com.backendpractice.blog.repositories.CategoryRepo;
import com.backendpractice.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        var toBeSavedCategory = this.mapper.map(categoryDto, CategoryEntity.class);
        return this.mapper.map(this.repo.save(toBeSavedCategory), CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        var oldCategory = this.getCategoryEntity(categoryId);
        oldCategory.setCategoryTitle(categoryDto.getCategoryTitle());
        oldCategory.setCategoryDescription(categoryDto.getCategoryDescription());
        return this.mapper.map(this.repo.save(oldCategory), CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        var toBeDeleted = this.getCategoryEntity(categoryId);
        this.repo.delete(toBeDeleted);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        return mapper.map(this.getCategoryEntity(categoryId), CategoryDto.class);
    }

    public CategoryEntity getCategoryEntity(Long categoryId) {
        return this.repo
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var catPost = this.repo.findAll(pageable);
        var allCats = catPost.getContent();
        var categoryDtos = allCats.stream().map(e -> mapper.map(e, CategoryDto.class)).toList();

        return CategoryResponse.builder()
                .content(categoryDtos)
                .pageNumber(catPost.getNumber())
                .pageSize(catPost.getSize())
                .totalElements(catPost.getTotalElements())
                .totalPages(catPost.getTotalPages())
                .lastPage(catPost.isLast())
                .build();
    }
}
