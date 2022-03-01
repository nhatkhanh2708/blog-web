package com.springk.blog.dtos.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "Username is not blank !")
    private String username;
    @NotBlank(message = "Password is not blank !")
    private String password;
}
