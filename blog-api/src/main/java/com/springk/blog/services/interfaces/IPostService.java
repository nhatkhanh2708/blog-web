package com.springk.blog.services.interfaces;

import com.springk.blog.dtos.PostDto;
import com.springk.blog.dtos.request.PostRequest;
import com.springk.blog.dtos.request.UpdatePostRequest;

import java.util.List;

public interface IPostService {
    List<PostDto> findAll();
    PostDto findById(long id);
    List<PostDto> findByTitle(String title);
    List<PostDto> findByCategory(String titleCategory);
    List<PostDto> findByUserId(long id);
    PostDto add(PostRequest postRequest);
    PostDto update(UpdatePostRequest updatePostRequest);
    void delete(long id);
}
