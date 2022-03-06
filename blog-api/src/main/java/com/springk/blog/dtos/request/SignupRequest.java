package com.springk.blog.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @Size(max = 70)
    @NotBlank(message = "Input username")
    private String username;
    @Size(max = 60)
    @NotBlank(message = "Input password")
    private String password;
    @Size(max = 50)
    @NotBlank(message = "Not empty")
    @Email(message = "Email isn't valid")
    private String email;
    private Set<String> roles;
}
