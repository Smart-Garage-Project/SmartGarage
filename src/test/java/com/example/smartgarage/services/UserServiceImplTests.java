package com.example.smartgarage.services;

import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.helpers.PasswordGenerator;
import com.example.smartgarage.helpers.UserMapper;
import com.example.smartgarage.models.NewUserDto;
import com.example.smartgarage.models.UpdateUserDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthorizationHelper authorizationHelper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordGenerator passwordGenerator;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private NewUserDto newUserDto;
    private UpdateUserDto updateUserDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("John Doe");
        user.setPassword("password");

        newUserDto = new NewUserDto();
        newUserDto.setUsername("John Doe");
        newUserDto.setEmail("john.doe@example.com");
        newUserDto.setPhoneNumber("123456789");

        updateUserDto = new UpdateUserDto();
        updateUserDto.setOldPassword("password");
        updateUserDto.setNewPassword("newPassword");
        updateUserDto.setConfirmPassword("newPassword");
    }

    @Test
    void testGetUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getUsers(user);

        assertNotNull(users);
        assertEquals(1, users.size());
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUsersFiltered() {
        Page<User> page = mock(Page.class);
        when(userRepository.findUsersByCriteria(anyString(), anyString(), anyString(), any(Pageable.class))).thenReturn(page);

        Page<User> usersPage = userService.getUsersFiltered(user, "username", "email", "phoneNumber", Pageable.unpaged());

        assertNotNull(usersPage);
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(userRepository, times(1)).findUsersByCriteria(anyString(), anyString(), anyString(), any(Pageable.class));
    }

    @Test
    void testGetById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.getById(1, user);

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getUsername());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testGetByUsername() {
        when(userRepository.findByUsername("John Doe")).thenReturn(Optional.of(user));

        User foundUser = userService.getByUsername("John Doe");

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("John Doe");
    }

    @Test
    void testCreate() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.registerFromDto(newUserDto)).thenReturn(user);

        User createdUser = userService.create(newUserDto, user);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getUsername());
        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdate() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.update(1, updateUserDto, user);

        assertNotNull(updatedUser);
        assertEquals("newPassword", updatedUser.getPassword());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDelete() {
        doNothing().when(userRepository).deleteById(1);

        userService.delete(1, user);

        verify(authorizationHelper, times(1)).checkIfCurrentUserIsEmployee(user);
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testGenerateNewPassword() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordGenerator.generatePassword()).thenReturn("newPassword");

        userService.generateNewPassword("john.doe@example.com");

        assertEquals("newPassword", user.getPassword());
        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
        verify(userRepository, times(1)).save(user);
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }
}