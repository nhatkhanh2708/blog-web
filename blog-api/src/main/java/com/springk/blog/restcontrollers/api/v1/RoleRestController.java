package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dtos.RoleDto;
import com.springk.blog.dtos.request.RoleRequest;
import com.springk.blog.dtos.response.ResponseDto;
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
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Get a role successed", role));
    }

    @GetMapping(value = {""})
    public ResponseEntity<?> getsAll(){
        log.info("Gets all role");
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        "Gets all successed",
                        _roleService.findAll()));
    }

    @GetMapping("/param")
    public ResponseEntity<?> getByName(@RequestParam(name = "name") String name){
        log.info("Get a role with name = "+name);
        RoleDto role = _roleService.findByName(name);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Get a role successed", role));
    }

    @PostMapping("")
    public ResponseEntity<?> addNewRole(@Valid @RequestBody RoleRequest roleRequest){
        return ResponseEntity.ok(
                new ResponseDto(HttpStatus.OK.value(),
                "Add role successed",
                _roleService.add(roleRequest)));
    }
}
