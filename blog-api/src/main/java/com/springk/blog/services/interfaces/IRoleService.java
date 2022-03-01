package com.springk.blog.services.interfaces;

import com.springk.blog.dtos.RoleDto;

import java.util.List;

public interface IRoleService {
    List<RoleDto> findAll();
    RoleDto findById(long id);
    RoleDto findByName(String name);
    RoleDto add(RoleDto roledto);
}
