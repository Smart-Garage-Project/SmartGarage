package com.example.smartgarage.repositories;

import com.example.smartgarage.models.RegisterDto;
import com.example.smartgarage.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();

    User getById(int id);

    User getByUsername(String username);

    User create(User user);

    User update(int id, User updatedUser);
}
