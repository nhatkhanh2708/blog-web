package com.springk.blog.services.impls;

import com.springk.blog.dal.entities.Role;
import com.springk.blog.dal.repositories.RoleRepository;
import com.springk.blog.dtos.RoleDto;
import com.springk.blog.dtos.request.RoleRequest;
import com.springk.blog.exceptions.ConflictException;
import com.springk.blog.exceptions.ObjectNotFoundException;
import com.springk.blog.services.interfaces.IRoleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    private Type _roleDtoTypes = new TypeToken<List<RoleDto>>(){}.getType();

    @Autowired
    private RoleRepository _roleRepository;

    @Autowired
    private ModelMapper _mapper;

    @Override
    @Transactional
    public List<RoleDto> findAll() {
        return _mapper.map(_roleRepository.findAll(), _roleDtoTypes);
    }

    @Override
    @Transactional
    public RoleDto findById(long id) {
        Role role = _roleRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Role isn't found with id = "+id));
        return _mapper.map(role, RoleDto.class);
    }

    @Override
    @Transactional
    public RoleDto findByName(String name) {
        Role role = _roleRepository.findByName(name)
                .orElseThrow(() -> new ObjectNotFoundException("Role isn't found with name = "+name));
        return _mapper.map(role, RoleDto.class);
    }

    @Override
    @Transactional
    public RoleDto add(RoleRequest roleRequest) {
        if(_roleRepository.findByName(roleRequest.getName()).isPresent()){
            throw new ConflictException("This name role was exist !");
        }
        Role role = _mapper.map(roleRequest, Role.class);
        return _mapper.map(_roleRepository.save(role), RoleDto.class);
    }
}
