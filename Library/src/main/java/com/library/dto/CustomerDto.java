package com.library.dto;

import com.library.model.City;
import com.library.model.Country;
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

    @Size(min = 10, max = 15, message = "Phone number contains 10-14 characters")
    private String phoneNumber;

    private String address;
    private String confirmPassword;
    private City city;
    private String image;
    private String country;
}
