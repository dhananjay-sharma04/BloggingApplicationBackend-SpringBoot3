package com.blogapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatgDto {

    @NotEmpty
    @Size(min = 3, message = "Title must be minimum of 3 characters!!")
    private String catgTitle;
    @NotEmpty
    @Size(min = 10, message = "Description must be minimum of 10 characters!!")
    private String catgDesc;
}