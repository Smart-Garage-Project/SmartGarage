package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.exceptions.ArgumentsMismatchException;
import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.*;
import com.example.smartgarage.services.contracts.CarService;
import com.example.smartgarage.services.contracts.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserMvcController {

    private final CarService carService;

    private final UserService userService;

    private final AuthenticationHelper authenticationHelper;

    public UserMvcController(CarService carService, UserService userService, AuthenticationHelper authenticationHelper) {
        this.carService = carService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String showAllUsers(Model model, HttpSession session,
                               @RequestParam(required = false) String username,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(defaultValue = "0") int page) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        Pageable pageable = PageRequest.of(page, 4);
        Page<User> usersPage = userService.getUsersFiltered(currentUser, username, email, phoneNumber, pageable);
        model.addAttribute("usersPage", usersPage);
        return "AllUsersView";
    }

    @GetMapping("/{id}")
    public String showUserDetails(@PathVariable int id, Model model, HttpSession session,
                                  @RequestParam(defaultValue = "0") int page) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        model.addAttribute("currentUser", currentUser);
        User user = userService.getById(id, currentUser);
        model.addAttribute("user", user);
        Pageable pageable = PageRequest.of(page, 3);
        Page<Car> carsPage = carService.getUserCars(id, currentUser, pageable);
        model.addAttribute("carsPage", carsPage);
        return "UserDetailsView";
    }

    @GetMapping("/create")
    public String showCreateUserPage(Model model) {
        model.addAttribute("newUser", new NewUserDto());
        return "CreateUserView";
    }

    @PostMapping("/create")
    public String handleCreateUser(@Valid @ModelAttribute("newUser") NewUserDto newUserDto,
                                   BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "CreateUserView";
        }

        try {
            User currentUser = authenticationHelper.tryGetCurrentUser(session);
            userService.create(newUserDto, currentUser);
            return "redirect:/employee-panel";
        } catch (EntityDuplicateException e) {
            bindingResult.rejectValue("username", "error.username", e.getMessage());
            bindingResult.rejectValue("email", "error.email", e.getMessage());
            bindingResult.rejectValue("phoneNumber", "error.phoneNumber", e.getMessage());
            return "CreateUserView";
        }
    }

    @GetMapping("/{id}/update")
    public String showUpdateUserPage(@PathVariable int id, Model model, HttpSession session) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        model.addAttribute("updateUser", new UpdateUserDto());
        User user = userService.getById(id, currentUser);
        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        return "UpdateUserView";
    }

    @PostMapping("/{id}/update")
    public String handleUpdateUser(@PathVariable int id, @Valid @ModelAttribute("updateUser") UpdateUserDto userDto,
                                   BindingResult bindingResult, Model model, HttpSession session) {

        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        User user = userService.getById(id, currentUser);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("currentUser", currentUser);
            return "UpdateUserView";
        }

        try {
            userService.update(id, userDto, currentUser);
            return "redirect:/users/" + id;
        } catch (ArgumentsMismatchException e) {
            bindingResult.rejectValue("oldPassword", "error.oldPassword", e.getMessage());
            bindingResult.rejectValue("newPassword", "error.newPassword", e.getMessage());
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", e.getMessage());
            return "UpdateUserView";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable int id, Model model, HttpSession session) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        model.addAttribute("currentUser", currentUser);
        User user = userService.getById(id, currentUser);
        model.addAttribute("user", user);
        userService.delete(id, currentUser);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/forgotten-password")
    public String showForgottenPasswordPage(Model model) {
        model.addAttribute("emailDto", new EmailDto());
        return "ForgottenPasswordView";
    }

    @PostMapping("/forgotten-password")
    public String handleForgottenPassword(@Valid @ModelAttribute("emailDto") EmailDto emailDto,
                                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "ForgottenPasswordView";
        }

        try {
            userService.generateNewPassword(emailDto.getEmail());
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            bindingResult.rejectValue("email", "error.email", e.getMessage());
            return "ForgottenPasswordView";
        }
    }
}
