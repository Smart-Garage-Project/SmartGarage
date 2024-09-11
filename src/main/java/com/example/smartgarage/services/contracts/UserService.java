package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.UpdateUserDto;
import com.example.smartgarage.models.User;

import java.util.List;

public interface UserService {

    List<User> getUsers(User currentUser);

    List<User> getUsersFiltered(User currentUser, String username, String email, String phoneNumber);

    User getById(int id, User currentUser);

    User getByUsername(String username);

    User create(User user, User currentUser);

    User update(int id, UpdateUserDto updateUserDto, User currentUser);

    void delete(int id, User user);

    void generateNewPassword(String email);
}
