package com.springk.blog.services.impls;

import com.springk.blog.dal.entities.Role;
import com.springk.blog.dal.entities.User;
import com.springk.blog.dal.repositories.RoleRepository;
import com.springk.blog.dal.repositories.UserRepository;
import com.springk.blog.dtos.UserDto;
import com.springk.blog.dtos.request.SignupRequest;
import com.springk.blog.exceptions.BadRequestException;
import com.springk.blog.exceptions.ConflictException;
import com.springk.blog.exceptions.ObjectNotFoundException;
import com.springk.blog.services.interfaces.IUserService;
import com.springk.blog.utils.enums.EnumRole;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private Type _userDtoTypes = new TypeToken<List<UserDto>>(){}.getType();

    @Autowired
    private UserRepository _userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository _roleRepository;

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
        User acc = _userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User isn't found with id =" +id));
        return _mapper.map(acc, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto findByUsername(String username) {
        User user = _userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User isn't found with username =" +username));
        return _mapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto findByEmail(String email) {
        User acc = _userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User isn't found with email =" +email));
        return _mapper.map(acc, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto add(SignupRequest request) {
        if(_userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new ConflictException("User existed with this username: "+request.getUsername());
        }

        if(_userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ConflictException("User existed with this email: "+request.getEmail());
        }

        User user = _mapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);

        Set<Role> roles = new HashSet<>();
        request.getRoles().forEach(role -> {
            roles.add(_roleRepository.findByName(role).orElseThrow(() -> new BadRequestException("Add a new user failed !")));
        });

        if(roles.size() == 0)
            roles.add(_roleRepository.findByName(EnumRole.USER.name()).get());

        user.setRoles(roles);

        return _mapper.map(_userRepository.save(user), UserDto.class);
    }

    @Override
    @Transactional
    public UserDto updateInfo(UserDto userDto) {
        User user = _userRepository.findById(userDto.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Can't update info user due to not found " +userDto.getUsername()));
        _mapper.map(userDto, user);
        return _mapper.map(_userRepository.save(user), UserDto.class);
    }

    @Override
    @Transactional
    public UserDto updateRole(String username, Set<String> roles) {
        User user = _userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Can't update roles user due to not found " + username));

        Set<Role> rolesSet = new HashSet<>();

        roles.forEach(role -> {
            rolesSet.add(_roleRepository.findByName(role).orElseThrow(() -> new BadRequestException("Can't update roles user !")));
        });

        if(rolesSet.size() == 0)
            rolesSet.add(_roleRepository.findByName(EnumRole.USER.name()).get());
        user.setRoles(rolesSet);

        return _mapper.map(_userRepository.save(user), UserDto.class);
    }

    @Override
    @Transactional
    public void blockUser(String username) {
        User user = _userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Can't block this user: " + username));
        user.setActive(false);
        _userRepository.save(user);
    }
}
