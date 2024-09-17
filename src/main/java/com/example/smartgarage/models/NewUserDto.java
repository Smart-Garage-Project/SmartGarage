package com.example.smartgarage.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class NewUserDto {

    @NotEmpty
    @Length(min = 8, max = 20)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 10, max = 10, message = "Phone number must be 10 characters long")
    private String phoneNumber;

    private boolean isEmployee;
}
