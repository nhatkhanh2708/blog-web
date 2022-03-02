package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dtos.UserDto;
import com.springk.blog.dtos.response.ResponseDto;
import com.springk.blog.dtos.response.ResponseSimple;
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
    public ResponseEntity<?> getsAll(){
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
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Get a account successed", user));
    }

    @GetMapping("/param")
    public ResponseEntity<?> getByEmail(
            @RequestParam(name = "email", required = true) String email){
        log.info("Get a account with email = " + email);
        UserDto user = _userService.findByEmail(email);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Get a account successed", user));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUsername(
            @PathVariable(name = "username", required = true) String username){
        log.info("Get a account with username = " + username);
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "Get a user successed",
                        _userService.findByUsername(username)));
    }

    @PutMapping("/{username}/update")
    public ResponseEntity<?> updateInfoUser(
            @PathVariable(name = "username", required = true) String username,
            @Valid @RequestBody UserDto userDto){
        log.info("Update user with username = "+username);
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "Updated the user successed",
                        _userService.updateInfo(userDto)
                )
        );
    }

    @PutMapping("/{username}/block")
    public ResponseEntity<?> blockUser(
            @PathVariable(name = "username", required = true) String username){

        log.info("Block user : "+username);
        _userService.blockUser(username);
        return ResponseEntity.ok().body(new ResponseSimple(
                HttpStatus.OK.value(),
                "Blocked the user successed"
        ));
    }

}
