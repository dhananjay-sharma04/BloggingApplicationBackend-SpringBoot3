package com.blogapp.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    @NotEmpty
    @Size(min = 5, max = 100, message = "Title must be minimum of 5 and maximum of 100 characters!!")
    private String title;

    @NotEmpty
    @Size(min = 10, max = 1000, message = "Content must be minimum of 10 and maximum of 1000 characters!!")
    private String content;

    private String imageName;
}