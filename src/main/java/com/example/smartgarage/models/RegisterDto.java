package com.example.smartgarage.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RegisterDto {

    @NotEmpty
    @Length(min = 2, max = 20)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 13, max = 13)
    private String phoneNumber;

    public @NotEmpty String getUsername() {
        return username;
    }

}
