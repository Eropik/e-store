package com.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    @Size(min = 2, max = 15, message = "First name should have 2-15 characters")
    private String firstName;

    @Size(min = 2, max = 15, message = "Last name should have 2-15 characters")
    private String lastName;

    private String username;

    @Size(min = 5, max = 15, message="Invalid password(5-15 char)")
    private String password;

    @Size(min = 5, max = 15, message="Invalid password(5-15 char)")
    private String repeatPassword;
}
