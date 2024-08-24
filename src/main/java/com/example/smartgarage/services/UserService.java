package com.example.smartgarage.services;

import com.example.smartgarage.models.RegisterDto;
import com.example.smartgarage.models.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getById(int id);

    User getByUsername(String username);

    User create(User user);
}
