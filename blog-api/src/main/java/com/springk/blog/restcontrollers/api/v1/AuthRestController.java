package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dtos.request.LoginRequest;
import com.springk.blog.dtos.request.SignupRequest;
import com.springk.blog.dtos.response.ResponseDto;
import com.springk.blog.dtos.response.ResponseJwt;
import com.springk.blog.security.jwt.JwtUtils;
import com.springk.blog.security.jwt.UserDetailsImpl;
import com.springk.blog.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping("/login")
    public ResponseEntity<?> getLogin(@Valid @RequestBody LoginRequest login) {
        log.info("User login with username = " + login.getUsername());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateToken(userDetails);

        log.info("Generated jwt for user successed");
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
        log.info("Creating a new user");
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "Created user successed",
                        _userService.add(signupRequest)));
    }
}
