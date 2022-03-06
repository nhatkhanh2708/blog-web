package com.springk.blog.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto extends AuditorAwareDto<String> implements Serializable {
    private Long id;
    @Size(max = 150)
    @NotBlank
    private String title;
    @Size(max = 1300)
    private String content;
    @Size(max = 200)
    private String slug;
    @NotEmpty
    @NotNull
    private UserDto user;
    private Set<CategoryDto> categories = new HashSet<>();
}
