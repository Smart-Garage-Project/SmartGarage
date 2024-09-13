package com.example.smartgarage.helpers;

import com.example.smartgarage.models.NewUserDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordGenerator passwordGenerator;

    @Autowired
    public UserMapper(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public User registerFromDto(NewUserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordGenerator.generatePassword());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmployee(dto.isEmployee());

        return user;
    }
}
