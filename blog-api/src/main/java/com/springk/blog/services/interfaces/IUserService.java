package com.springk.blog.services.interfaces;

import com.springk.blog.dtos.UserDto;

import java.util.List;

public interface IUserService {
    List<UserDto> findAll();
    UserDto findById(long id);
    UserDto findByUsername(String username);
    UserDto findByEmail(String email);
    UserDto add(UserDto userdto);
}
