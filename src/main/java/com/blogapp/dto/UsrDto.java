package com.blogapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrDto {

    @NotEmpty
    @Size(min = 3, message = "Username must be minimum of 3 characters!!")
    private String name;
    @NotEmpty
    @Email(message = "Email address is not Valid !!")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$", message = """
            - at least 6 characters
            - must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number
            - Can contain special characters""")
    private String  password;
    private String imageName;
    @NotEmpty
    private String about;
}