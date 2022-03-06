package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dtos.UserDto;
import com.springk.blog.dtos.request.UpdateUserRequest;
import com.springk.blog.dtos.response.ResponseDto;
import com.springk.blog.dtos.response.ResponseSimple;
import com.springk.blog.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        log.info("Get all account");
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

    @PutMapping("/update")
    public ResponseEntity<?> updateInfoUser(
            @Valid @RequestBody UpdateUserRequest updateUserRequest){
        log.info("Update user with username = "+updateUserRequest.getId());
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "Updated the user successed",
                        _userService.updateInfo(updateUserRequest)
                )
        );
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<?> blockUser(
            @PathVariable(name = "id", required = true) long id){

        log.info("Blocking user : "+id);
        _userService.blockUser(id);
        return ResponseEntity.ok().body(new ResponseSimple(
                HttpStatus.OK.value(),
                "Blocked the user successed"
        ));
    }

}
