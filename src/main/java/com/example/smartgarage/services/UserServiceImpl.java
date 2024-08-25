package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers(User currentUser) {
        checkIfCurrentUserIsEmployee(currentUser);
        return userRepository.getUsers();
    }

    @Override
    public User getById(int id, User currentUser) {
        checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(currentUser, id);
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public User update(int id, User updatedUser, User user) {
        checkIfCurrentUserIsSameAsTargetUser(user, id);
        return userRepository.update(id, updatedUser);
    }

    private void checkIfCurrentUserIsEmployee(User user) {
        if (!user.isEmployee()) {
            throw new AuthorizationException("You have to be employee to browse this information.");
        }
    }

    private void checkIfCurrentUserIsSameAsTargetUser(User user, int id) {
        if (user.getId() != id) {
            throw new AuthorizationException("You are not authorized to browse this information.");
        }
    }

    private void checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(User user, int id) {
        if (!user.isEmployee() && user.getId() != id) {
            throw new AuthorizationException("You are not authorized to browse this information.");
        }
    }
}
