package com.example.smartgarage.helpers;

import com.example.smartgarage.models.RegisterDto;
import com.example.smartgarage.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User getUserFromRegisterDto(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        return user;
    }
}
