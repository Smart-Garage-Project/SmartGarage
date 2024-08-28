package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.User;

import java.util.List;

public interface UserService {

    List<User> getUsers(User currentUser);

    User getById(int id, User currentUser);

    User getByUsername(String username);

    User create(User user);

    User update(int id, User updatedUser, User user);
}
