package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.helpers.UserMapper;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.RegisterDto;
import com.example.smartgarage.models.UpdateUserDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.CarService;
import com.example.smartgarage.services.contracts.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RestUserController {

    private final UserService userService;

    private final CarService carService;

    private final AuthenticationHelper authenticationHelper;

    private final UserMapper userMapper;

    @Autowired
    public RestUserController(UserService userService, CarService carService, AuthenticationHelper authenticationHelper, UserMapper userMapper) {
        this.userService = userService;
        this.carService = carService;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
    }

    @GetMapping
    public Page<User> getUsers(@RequestHeader HttpHeaders headers,
                               @RequestParam(required = false) String username,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(defaultValue = "0") int page){
        User user = authenticationHelper.tryGetUser(headers);
        Pageable pageable = PageRequest.of(page, 10);
        return userService.getUsersFiltered(user, username, email, phoneNumber, pageable);
    }

    @GetMapping("/{id}/cars")
    public Page<Car> getCars(@RequestHeader HttpHeaders headers, @PathVariable int id,
                             @RequestParam(defaultValue = "0") int page) {
        User currentUser = authenticationHelper.tryGetUser(headers);
        Pageable pageable = PageRequest.of(page, 10);
        return carService.getUserCars(id, currentUser, pageable);
    }

    @GetMapping("/{id}")
    public User getUserById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        return userService.getById(id, user);
    }

    @PostMapping("/register")
    public User create(@RequestHeader HttpHeaders headers, @Valid @RequestBody RegisterDto registerDto) {
        User currentUser = authenticationHelper.tryGetUser(headers);
        User user = userMapper.registerFromDto(registerDto);
        return userService.create(user, currentUser);
    }

    @PutMapping("/{id}")
    public User update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        User user = authenticationHelper.tryGetUser(headers);
        return userService.update(id, updateUserDto, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        userService.delete(id, user);
    }

    @PutMapping("/forgotten-password")
    public void forgottenPassword(@RequestParam String email) {
        userService.generateNewPassword(email);
    }
}
