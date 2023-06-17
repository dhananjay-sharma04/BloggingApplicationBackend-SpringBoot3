package com.blogapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmntDto {

    @NotEmpty
    private String content;

    @NotEmpty
    private Long postId;

    @NotEmpty
    private Long userId;
}