package com.springk.blog.services.impls;

import com.springk.blog.dal.entities.User;
import com.springk.blog.dal.repositories.UserRepository;
import com.springk.blog.dtos.UserDto;
import com.springk.blog.services.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class UserService implements IUserService {

    private Type _userDtoTypes = new TypeToken<List<UserDto>>(){}.getType();

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private ModelMapper _mapper;

    @Override
    @Transactional
    public List<UserDto> findAll() {
        return _mapper.map(_userRepository.findAll(), _userDtoTypes);
    }

    @Override
    @Transactional
    public UserDto findById(long id) {
        User acc = _userRepository.findById(id).orElse(null);
        return acc != null ? _mapper.map(acc, UserDto.class) : null;
    }

    @Override
    @Transactional
    public UserDto findByUsername(String username) {
        User acc = _userRepository.findByUsername(username).orElse(null);
        return acc != null ? _mapper.map(acc, UserDto.class) : null;
    }

    @Override
    @Transactional
    public UserDto findByEmail(String email) {
        User acc = _userRepository.findByEmail(email).orElse(null);
        return acc != null ? _mapper.map(acc, UserDto.class) : null;
    }

    @Override
    @Transactional
    public UserDto add(UserDto userdto) {
        User user = _mapper.map(userdto, User.class);
        return _mapper.map(_userRepository.save(user), UserDto.class);
    }
}
