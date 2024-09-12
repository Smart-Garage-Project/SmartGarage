package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserMvcController {

    private final UserService userService;

    private final AuthenticationHelper authenticationHelper;

    public UserMvcController(UserService userService, AuthenticationHelper authenticationHelper) {
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
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> usersPage = userService.getUsersFiltered(currentUser, username, email, phoneNumber, pageable);
        model.addAttribute("usersPage", usersPage);
        return "users";
    }
}
