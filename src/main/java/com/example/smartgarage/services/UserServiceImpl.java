package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.ArgumentsMismatchException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.helpers.PasswordGenerator;
import com.example.smartgarage.helpers.UserMapper;
import com.example.smartgarage.models.NewUserDto;
import com.example.smartgarage.models.UpdateUserDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.UserRepository;
import com.example.smartgarage.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final AuthorizationHelper authorizationHelper;

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final PasswordGenerator passwordGenerator;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, AuthorizationHelper authorizationHelper, UserRepository userRepository, EmailService emailService, PasswordGenerator passwordGenerator) {
        this.userMapper = userMapper;
        this.authorizationHelper = authorizationHelper;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public List<User> getUsers(User currentUser) {
        authorizationHelper.checkIfCurrentUserIsEmployee(currentUser);
        return userRepository.findAll();
    }

    @Override
    public Page<User> getUsersFiltered(User currentUser, String username, String email, String phoneNumber, Pageable pageable) {
        authorizationHelper.checkIfCurrentUserIsEmployee(currentUser);
        return userRepository.findUsersByCriteria(username, email, phoneNumber, pageable);
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
    public User create(NewUserDto newUserDto, User currentUser) {
        authorizationHelper.checkIfCurrentUserIsEmployee(currentUser);

        userRepository.findByUsername(newUserDto.getUsername()).ifPresent(u -> {
            throw new EntityDuplicateException("User", "username", newUserDto.getUsername());
        });

        userRepository.findByEmail(newUserDto.getEmail()).ifPresent(u -> {
            throw new EntityDuplicateException("User", "email", newUserDto.getEmail());
        });

        userRepository.findByPhoneNumber(newUserDto.getPhoneNumber()).ifPresent(u -> {
            throw new EntityDuplicateException("User", "phone number", newUserDto.getPhoneNumber());
        });

        User newUser = userMapper.registerFromDto(newUserDto);

        emailService.sendEmail(newUser.getEmail(), "Welcome to SmartGarage",
                String.format("Your username is: %s\nYour password is: %s\nRemember to keep your Username and Password secure",
                        newUser.getUsername(), newUser.getPassword()));

        return userRepository.save(newUser);
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

    @Override
    public void generateNewPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User", "email", email));
        user.setPassword(passwordGenerator.generatePassword());
        userRepository.save(user);

        emailService.sendEmail(email, "New Password",
                String.format("Your new password is: %s\nRemember to keep your Username and Password secure",
                        user.getPassword()));
    }
}
