package com.springk.blog.services.interfaces;

import com.springk.blog.dtos.PostDto;
import com.springk.blog.dtos.request.PostRequest;

import java.util.List;
import java.util.Set;

public interface IPostService {
    List<PostDto> findAll();
    PostDto findById(long id);
    List<PostDto> findByTitle(String title);
    List<PostDto> findByCategory(Set<String> categories);
    PostDto add(PostRequest postRequest);
    PostDto update(PostDto postDto);
}
