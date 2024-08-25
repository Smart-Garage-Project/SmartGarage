package com.example.smartgarage.helpers;

import com.example.smartgarage.models.RegisterDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserService userService;

    @Autowired
    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public User fromDto(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhoneNumber());
        user.setEmployee(false);

        return user;
    }

    public User registerFromDto(RegisterDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhoneNumber());
        user.setEmployee(false);

        return user;
    }
}
