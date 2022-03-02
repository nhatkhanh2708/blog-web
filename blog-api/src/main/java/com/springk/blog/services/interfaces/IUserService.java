package com.springk.blog.services.interfaces;

import com.springk.blog.dtos.UserDto;
import com.springk.blog.dtos.request.SignupRequest;

import java.util.List;
import java.util.Set;

public interface IUserService {
    List<UserDto> findAll();
    UserDto findById(long id);
    UserDto findByUsername(String username);
    UserDto findByEmail(String email);
    UserDto add(SignupRequest request);
    UserDto updateInfo(UserDto userDto);
    UserDto updateRole(String username, Set<String> roles);
    void blockUser(String username);
}
