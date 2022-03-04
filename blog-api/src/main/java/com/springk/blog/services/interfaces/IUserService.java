package com.springk.blog.services.interfaces;

import com.springk.blog.dtos.UserDto;
import com.springk.blog.dtos.request.SignupRequest;
import com.springk.blog.dtos.request.UpdateUserRequest;

import java.util.List;
import java.util.Set;

public interface IUserService {
    List<UserDto> findAll();
    UserDto findById(long id);
    UserDto findByUsername(String username);
    UserDto findByEmail(String email);
    UserDto add(SignupRequest request);
    UserDto updateInfo(UpdateUserRequest updateUserRequest);
    UserDto updateRole(String username, Set<String> roles);
    void blockUser(long id);
}
