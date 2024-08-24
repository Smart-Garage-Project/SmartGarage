package com.example.smartgarage.repositories;

import com.example.smartgarage.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();
}
