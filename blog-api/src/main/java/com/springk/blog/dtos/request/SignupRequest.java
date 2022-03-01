package com.springk.blog.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springk.blog.dtos.RoleDto;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
public class SignupRequest implements Serializable {
    @Size(max = 70)
    @NotBlank(message = "Input username")
    private String username;
    @Size(max = 60)
    @NotBlank(message = "Input password")
    @JsonIgnore
    private String password;
    @Size(max = 50)
    @NotBlank(message = "Don't input empty")
    @Email(message = "Email isn't valid")
    private String email;
    private Set<String> roles;
}
