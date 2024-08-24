//package com.example.smartgarage.helpers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CreateUserMapper {
//
//    private UserService userService;
//
//    @Autowired
//    public CreateUserMapper(UserService userService) {
//        this.userService = userService;
//    }
//
//    public User fromDto(RegisterDto registerDto) {
//        User user = new User();
//        user.setUsername(registerDto.getUsername());
//        user.setPassword(registerDto.getPassword());
//        user.setFirstName(registerDto.getFirstName());
//        user.setLastName(registerDto.getLastName());
//        user.setEmail(registerDto.getEmail());
//        user.setActive(true);
//
//        return user;
//    }
//
//    public User registerFromDto(RegisterDto dto) {
//        User user = new User();
//        user.setUsername(dto.getUsername());
//        user.setPassword(dto.getPassword());
//        user.setFirstName(dto.getFirstName());
//        user.setLastName(dto.getLastName());
//        user.setEmail(dto.getEmail());
//        user.setAdmin(false);
//        user.setModerator(false);
//        user.setActive(true);
//
//        return user;
//    }
//}
