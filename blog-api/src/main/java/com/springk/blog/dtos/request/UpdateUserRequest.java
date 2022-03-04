package com.springk.blog.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @NotNull
    @NotEmpty
    private Long id;
    @Size(max = 60)
    @NotBlank
    private String password;
    @Size(max = 500)
    private String intro;
    @Size(max = 1000)
    private String profile;
}
