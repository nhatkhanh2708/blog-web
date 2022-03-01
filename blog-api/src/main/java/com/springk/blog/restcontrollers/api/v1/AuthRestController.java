package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dal.repositories.RoleRepository;
import com.springk.blog.dal.repositories.UserRepository;
import com.springk.blog.dtos.RoleDto;
import com.springk.blog.dtos.UserDto;
import com.springk.blog.dtos.request.LoginRequest;
import com.springk.blog.dtos.request.SignupRequest;
import com.springk.blog.dtos.response.ResponseDto;
import com.springk.blog.dtos.response.ResponseFailed;
import com.springk.blog.dtos.response.ResponseJwt;
import com.springk.blog.security.jwt.JwtUtils;
import com.springk.blog.security.jwt.UserDetailsImpl;
import com.springk.blog.services.impls.RoleService;
import com.springk.blog.services.impls.UserService;
import com.springk.blog.services.interfaces.IRoleService;
import com.springk.blog.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthRestController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IUserService _userService;

    @Autowired
    private IRoleService _roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> getLogin(@Valid @RequestBody LoginRequest login) {
        log.info("User login with username = " + login.getUsername());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new ResponseJwt(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                        .collect(Collectors.toList())));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (_userService.findByUsername(signupRequest.getUsername()) == null) {
            return ResponseEntity.badRequest().body(new ResponseFailed(HttpStatus.BAD_REQUEST.value(), "Error: Username is already taken!"));
        }

        if (_userService.findByEmail(signupRequest.getEmail()) == null) {
            return ResponseEntity.badRequest().body(new ResponseFailed(HttpStatus.BAD_REQUEST.value(), "Error: Email is already in use!"));
        }

        UserDto userDto = new UserDto();
        userDto.setUsername(signupRequest.getUsername());
        userDto.setEmail(signupRequest.getEmail());
        userDto.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userDto.setActive(true);

        Set<String> strRoles = signupRequest.getRoles();
        Set<RoleDto> rolesdto = new HashSet<>();

        if (strRoles == null || strRoles.size() == 0) {
            RoleDto roledto = _roleService.findByName("USER");
            rolesdto.add(roledto);
        } else {
            strRoles.forEach(role -> {
                        RoleDto roledto = _roleService.findByName(role);
                        if(roledto != null)
                            rolesdto.add(roledto);
                    }
            );
        }

        userDto.setRoles(rolesdto);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Created user successed", _userService.add(userDto)));
    }
}
