package com.springk.blog.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username is not blank !")
    @Size(max = 70)
    private String username;
    @NotBlank(message = "Password is not blank !")
    private String password;
}
