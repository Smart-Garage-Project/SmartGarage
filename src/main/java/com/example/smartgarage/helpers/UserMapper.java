package com.example.smartgarage.helpers;

import com.example.smartgarage.models.RegisterDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.EmailService;
import com.example.smartgarage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserService userService;

    private final PasswordGenerator passwordGenerator;

    private final EmailService emailService;

    @Autowired
    public UserMapper(UserService userService, PasswordGenerator passwordGenerator, EmailService emailService) {
        this.userService = userService;
        this.passwordGenerator = passwordGenerator;
        this.emailService = emailService;
    }

    public User fromDto(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
//        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setEmployee(false);

        return user;
    }

    public User registerFromDto(RegisterDto dto) {
        String generatedPassword = passwordGenerator.generatePassword();

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(generatedPassword);
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmployee(false);

        emailService.sendEmail(user.getEmail(), "Welcome to SmartGarage",
                String.format("Your username is: %s\nYour password is: %s\nRemember to keep your Username and Password secure",
                        user.getUsername(), generatedPassword));

        return user;
    }
}
