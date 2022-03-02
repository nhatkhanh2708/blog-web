package com.springk.blog.services.interfaces;

import com.springk.blog.dtos.CategoryDto;
import com.springk.blog.dtos.request.CategoryRequest;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> findAll();
    CategoryDto findById(long id);
    CategoryDto findByTitle(String title);
    CategoryDto add(CategoryRequest categoryRequest);
    CategoryDto update(CategoryDto categoryDto);
}
