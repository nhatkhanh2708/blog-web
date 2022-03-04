package com.springk.blog.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CategoryDto extends AuditorAwareDto<String> implements Serializable {
    private Long id;
    @Size(max = 200)
    @NotBlank
    private String title;
}
