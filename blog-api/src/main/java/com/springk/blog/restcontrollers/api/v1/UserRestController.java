package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dtos.UserDto;
import com.springk.blog.dtos.response.ResponseDto;
import com.springk.blog.dtos.response.ResponseFailed;
import com.springk.blog.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserRestController {

    @Autowired
    private IUserService _userService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        log.info("Gets all account");
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "Gets all success",
                        _userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        log.info("Get a account with id = " + id);
        UserDto user = _userService.findById(id);
        return user != null ?
                ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Get account successed", user))
                : new ResponseEntity<>(
                        new ResponseFailed(HttpStatus.NOT_FOUND.value(), "Not found account"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/param")
    public ResponseEntity<?> getByEmail(
            @RequestParam(name = "email", required = true) String email){
        log.info("Get a account with email = " + email);
        if(email == null || email.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseFailed(HttpStatus.BAD_REQUEST.value(), "Email is not blank !"));
        }
        UserDto user = _userService.findByEmail(email);
        return user != null ?
                ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Get account successed", user))
                : new ResponseEntity<>(
                new ResponseFailed(HttpStatus.NOT_FOUND.value(), "Not found account"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getby")
    public ResponseEntity<?> getByUsername(
            @RequestParam(name = "username", required = true) String username){
        log.info("Get a account with username = " + username);
        if(username == null || username.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseFailed(HttpStatus.BAD_REQUEST.value(), "Username is not blank !"));
        }

        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "Get user successed",
                        _userService.findByUsername(username)));
    }

    @PostMapping("")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto){
        log.info("Add new user");
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "Add new user successed",
                        _userService.add(userDto)));
    }
}
