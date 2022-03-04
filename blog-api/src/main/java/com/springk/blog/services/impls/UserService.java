package com.springk.blog.services.impls;

import com.springk.blog.dal.entities.Role;
import com.springk.blog.dal.entities.User;
import com.springk.blog.dal.repositories.RoleRepository;
import com.springk.blog.dal.repositories.UserRepository;
import com.springk.blog.dtos.UserDto;
import com.springk.blog.dtos.request.SignupRequest;
import com.springk.blog.dtos.request.UpdateUserRequest;
import com.springk.blog.exceptions.BadRequestException;
import com.springk.blog.exceptions.ConflictException;
import com.springk.blog.exceptions.ForbiddenException;
import com.springk.blog.exceptions.ObjectNotFoundException;
import com.springk.blog.services.interfaces.IUserService;
import com.springk.blog.utils.enums.EnumRole;
import com.springk.blog.utils.helper.BcryptHelper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if(!BcryptHelper.checkPassEncodeByBcrypt(request.getPassword())){
            throw new BadRequestException("Password must encode by bcrypt(10) !");
        }

        User user = _mapper.map(request, User.class);
        user.setPassword(request.getPassword());
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
    public UserDto updateInfo(UpdateUserRequest updateUserRequest) {
        User user = _userRepository.findById(updateUserRequest.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Not found username with id: " +updateUserRequest.getId()));

        String nameAuth = SecurityContextHolder.getContext().getAuthentication().getName();

        //Can't update the user without user own
        if(nameAuth != user.getUsername())
            throw new ForbiddenException("You are not permission to access this user !");

        _mapper.map(updateUserRequest, user);
        return _mapper.map(_userRepository.save(user), UserDto.class);
    }

    @Deprecated
    @Override
    @Transactional
    public UserDto updateRole(String username, Set<String> roles) {
        User user = _userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Not found username: " + username));

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
    public void blockUser(long id) {
        User user = _userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Not found userid: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName() != user.getUsername()
            || !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            throw new ForbiddenException("You are not permission to access this user !");

        user.setActive(false);
        _userRepository.save(user);
    }
}
