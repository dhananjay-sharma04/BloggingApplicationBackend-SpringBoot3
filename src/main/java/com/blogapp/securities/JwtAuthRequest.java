package com.blogapp.securities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthRequest {

    @NotEmpty
    @Email(message = "Email address is not Valid !!")
    private String username; // Here User email acts as username

    @NotEmpty
//    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$",
//            message = "- at least 6 characters\n" + "- must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number\n" + "- Can contain special characters")
    private String password;
}