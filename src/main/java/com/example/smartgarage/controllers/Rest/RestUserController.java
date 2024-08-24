package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.helpers.UserMapper;
import com.example.smartgarage.models.RegisterDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RestUserController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserMapper userMapper;
    @Autowired
    public RestUserController(UserService userService, AuthenticationHelper authenticationHelper, UserMapper userMapper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<User> getUsers(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            checkIfCurrentUserIsEmployee(user);
            return userService.getUsers();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(user, id);
            return userService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    @PostMapping
    public User create(@Valid @RequestBody RegisterDto registerDto){
        try {
            User user = userMapper.getUserFromRegisterDto(registerDto);
            return userService.create(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    private void checkIfCurrentUserIsEmployee(User user) {
        if(!user.isEmployee()){
            throw new AuthorizationException("You have to be employee to browse this information.");
        }
    }
    private void checkIfCurrentUserIsSameAsTargetUser(User user, int id) {
        if(user.getId() != id){
            throw new AuthorizationException("You are not authorized to browse this information.");
        }
    }
    private void checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(User user, int id) {
        if(!user.isEmployee() && user.getId() != id){
            throw new AuthorizationException("You are not authorized to browse this information.");
        }
    }

}
