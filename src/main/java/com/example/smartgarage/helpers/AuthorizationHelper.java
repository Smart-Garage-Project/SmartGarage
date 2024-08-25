package com.example.smartgarage.helpers;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.models.User;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHelper {
    public void checkIfCurrentUserIsEmployee(User user) {
        if (!user.isEmployee()) {
            throw new AuthorizationException("You have to be employee to browse this information.");
        }
    }

    public void checkIfCurrentUserIsSameAsTargetUser(User user, int id) {
        if (user.getId() != id) {
            throw new AuthorizationException("You are not authorized to browse this information.");
        }
    }

    public void checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(User user, int id) {
        if (!user.isEmployee() && user.getId() != id) {
            throw new AuthorizationException("You are not authorized to browse this information.");
        }
    }
}
