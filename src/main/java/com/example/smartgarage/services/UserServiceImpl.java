package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.ArgumentsMismatchException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.models.UpdateUserDto;
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

    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(AuthorizationHelper authorizationHelper, UserRepository userRepository, EmailService emailService) {
        this.authorizationHelper = authorizationHelper;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public List<User> getUsers(User currentUser) {
        authorizationHelper.checkIfCurrentUserIsEmployee(currentUser);
        return userRepository.findAll();
    }

    @Override
    public User getById(int id, User currentUser) {
        authorizationHelper.checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(currentUser, id);
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }

    @Override
    public User create(User user, User currentUser) {
        authorizationHelper.checkIfCurrentUserIsEmployee(currentUser);

        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new EntityDuplicateException("User", "username", user.getUsername());
        });

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        });

        userRepository.findByPhoneNumber(user.getPhoneNumber()).ifPresent(u -> {
            throw new EntityDuplicateException("User", "phone number", user.getPhoneNumber());
        });

        emailService.sendEmail(user.getEmail(), "Welcome to SmartGarage",
                String.format("Your username is: %s\nYour password is: %s\nRemember to keep your Username and Password secure",
                        user.getUsername(), user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(int id, UpdateUserDto updateUserDto, User currentUser) {
        authorizationHelper.checkIfCurrentUserIsSameAsTargetUser(currentUser, id);
        User userToUpdate = getById(id, currentUser);

        if (!updateUserDto.getOldPassword().equals(userToUpdate.getPassword())) {
            throw new ArgumentsMismatchException("Old password is incorrect");
        }

        if (!updateUserDto.getNewPassword().equals(updateUserDto.getConfirmPassword())) {
            throw new ArgumentsMismatchException("New password and confirm password do not match");
        }

        userToUpdate.setPassword(updateUserDto.getNewPassword());

        return userRepository.save(userToUpdate);
    }

    @Override
    public void delete(int id, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        userRepository.deleteById(id);
    }
}
