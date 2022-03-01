package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dtos.RoleDto;
import com.springk.blog.dtos.response.ResponseDto;
import com.springk.blog.dtos.response.ResponseFailed;
import com.springk.blog.services.interfaces.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/role")
@Slf4j
public class RoleRestController {
    @Autowired
    private IRoleService _roleService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        log.info("Get a role with id = "+id);
        RoleDto role = _roleService.findById(id);
        return role != null ?
                ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Get role successed", role))
                : new ResponseEntity<>(
                new ResponseFailed(HttpStatus.NOT_FOUND.value(), "Not found the role with this id"), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> getAll(){
        log.info("Gets all role");
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "Gets all successed",
                        _roleService.findAll()));
    }

    @PostMapping("")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleDto roledto){
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(),
                "Add role successed",
                _roleService.add(roledto)));
    }
}
