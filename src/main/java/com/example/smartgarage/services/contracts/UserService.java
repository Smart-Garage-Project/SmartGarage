package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.UpdateUserDto;
import com.example.smartgarage.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<User> getUsers(User currentUser);

    Page<User> getUsersFiltered(User currentUser, String username, String email, String phoneNumber, Pageable pageable);

    User getById(int id, User currentUser);

    User getByUsername(String username);

    User create(User user, User currentUser);

    User update(int id, UpdateUserDto updateUserDto, User currentUser);

    void delete(int id, User user);

    void generateNewPassword(String email);
}
