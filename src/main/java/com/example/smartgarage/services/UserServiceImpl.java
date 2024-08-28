package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.UserRepository;
import com.example.smartgarage.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private final AuthorizationHelper authorizationHelper;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(AuthorizationHelper authorizationHelper, UserRepository userRepository) {
        this.authorizationHelper = authorizationHelper;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers(User currentUser) {
        authorizationHelper.checkIfCurrentUserIsEmployee(currentUser);
        return userRepository.getUsers();
    }

    @Override
    public User getById(int id, User currentUser) {
        authorizationHelper.checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(currentUser, id);
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public User create(User user) {

        boolean duplicateExists = true;

        try {
            userRepository.getByUsername(user.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("User", "username", user.getUsername());
        }

        return userRepository.create(user);
    }

    @Override
    public User update(int id, User updatedUser, User user) {
        authorizationHelper.checkIfCurrentUserIsSameAsTargetUser(user, id);
        return userRepository.update(id, updatedUser);
    }
}
