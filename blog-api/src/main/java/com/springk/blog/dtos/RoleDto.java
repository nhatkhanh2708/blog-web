package com.springk.blog.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private Long id;
    @NotBlank
    @Size(max = 50)
    private String name;
}
