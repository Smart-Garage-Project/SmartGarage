package com.example.smartgarage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @JsonIgnore
    @Column(name = "password")
    private String password;
    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "is_employee")
    private boolean isEmployee;

    public User() {
    }

    public User(String username, String password, String email, String phone, boolean isEmployee) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.isEmployee = isEmployee;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }
}