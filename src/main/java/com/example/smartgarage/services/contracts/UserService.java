package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.UpdateUserDto;
import com.example.smartgarage.models.User;

import java.util.List;

public interface UserService {

    List<User> getUsers(User currentUser);

    User getById(int id, User currentUser);

    User getByUsername(String username);

    User create(User user, User currentUser);

    User update(int id, UpdateUserDto updateUserDto, User currentUser);

    void delete(int id, User user);
}
