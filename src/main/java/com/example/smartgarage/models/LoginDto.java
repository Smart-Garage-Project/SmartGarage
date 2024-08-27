package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.Length;

public class LoginDto {

    @NotEmpty
    @Length(min = 2, max = 20)
    private String username;

    @NotEmpty
    @Length(min = 8, max = 20)
    private String password;

    public LoginDto() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
