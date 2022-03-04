package com.springk.blog.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto extends AuditorAwareDto<String> implements Serializable {
    private Long id;
    @Size(max = 70)
    @NotBlank
    private String username;
    @Size(max = 60)
    @NotBlank
    @JsonIgnore
    private String password;
    @Size(max = 50)
    @NotBlank
    @Email
    private String email;
    @Size(max = 500)
    private String intro;
    @Size(max = 1000)
    private String profile;
    private Set<RoleDto> roles;
    private boolean active;
}
