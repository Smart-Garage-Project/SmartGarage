package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;

public class RegisterDto extends LoginDto {

    @NotEmpty
    private String passwordConfirm;

    @NotEmpty
    private String email;

    @NotEmpty
    private String phoneNumber;

    public @NotEmpty String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(@NotEmpty String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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
