package com.springk.blog.services.interfaces;

import com.springk.blog.dtos.RoleDto;
import com.springk.blog.dtos.request.RoleRequest;

import java.util.List;

public interface IRoleService {
    List<RoleDto> findAll();
    RoleDto findById(long id);
    RoleDto findByName(String name);
    RoleDto add(RoleRequest roleRequest);
}
