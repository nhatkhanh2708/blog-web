package com.springk.blog.dtos.request;

import com.springk.blog.dtos.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @Size(max = 150)
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotEmpty
    @NotNull
    private String username;
    @NotEmpty
    private Set<String> categories = new HashSet<>();
}
