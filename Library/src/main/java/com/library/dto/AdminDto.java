package com.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    @Size(min = 2, max = 15, message = "Invalid first name(3-15 char)")
    private String firstName;
    @Size(min = 2, max = 15, message = "Invalid last name(3-15 char)")
    private String lastName;

    private String username;

    @Size(min = 5, max = 15, message="Invalid password(5-15 char)")
    private String password;

    @Size(min = 5, max = 15, message="Invalid password(5-15 char)")
    private String repeatPassword;
}
