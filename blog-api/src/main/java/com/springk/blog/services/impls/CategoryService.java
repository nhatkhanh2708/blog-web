package com.springk.blog.services.impls;

import com.springk.blog.dal.entities.Category;
import com.springk.blog.dal.repositories.CategoryRepository;
import com.springk.blog.dtos.CategoryDto;
import com.springk.blog.dtos.request.CategoryRequest;
import com.springk.blog.exceptions.ConflictException;
import com.springk.blog.exceptions.ObjectNotFoundException;
import com.springk.blog.services.interfaces.ICategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private Type _categoryDtoTypes = new TypeToken<List<CategoryDto>>(){}.getType();

    @Autowired
    private CategoryRepository _categoryRepository;

    @Autowired
    private ModelMapper _mapper;

    @Override
    @Transactional
    public List<CategoryDto> findAll() {
        return _mapper.map(_categoryRepository.findAll(), _categoryDtoTypes);
    }

    @Override
    @Transactional
    public CategoryDto findById(long id) {
        Category category = _categoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Not found category with id = "+id));
        return _mapper.map(category, CategoryDto.class);
    }

    @Override
    @Transactional
    public CategoryDto findByTitle(String title) {
        Category category = _categoryRepository.findByTitle(title)
                .orElseThrow(() -> new ObjectNotFoundException("Not found category with title "+title));
        return _mapper.map(category, CategoryDto.class);
    }

    @Override
    @Transactional
    public CategoryDto add(CategoryRequest categoryRequest) {
        if(_categoryRepository.findByTitle(categoryRequest.getTitle()).isPresent()){
            throw new ConflictException("Category existed with title "+categoryRequest.getTitle());
        }
        Category category = _mapper.map(categoryRequest, Category.class);
        return _mapper.map(category, CategoryDto.class);
    }

    @Override
    @Transactional
    public CategoryDto update(CategoryDto categoryDto) {
        Category category = _categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Not found category with id "+categoryDto.getId()));
        _mapper.map(categoryDto, category);
        return _mapper.map(_categoryRepository.save(category), CategoryDto.class);
    }
}
