package com.example.smartgarage.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.Length;

public class RegisterDto {

    @NotEmpty
    @Length(min = 2, max = 20)
    @Unique
    private String username;

    @NotEmpty
    @Email
    @Unique
    private String email;

    @NotEmpty
    @Unique
    @Length(min = 13, max = 13)
    private String phoneNumber;

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty String email) {
        this.email = email;
    }

    public @NotEmpty String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotEmpty String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
