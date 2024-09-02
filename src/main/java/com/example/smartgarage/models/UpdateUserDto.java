package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UpdateUserDto {

    private final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[\\+\\-\\*\\^])[A-Za-z\\d\\+\\-\\*\\^]{8,}$";

    private final String MESSAGE = "Password must contain at least 8 symbols and should contain a capital letter, digit, and special symbol (+, -, *, ^, etc.)";

    @NotEmpty
    @Length(min = 8, max = 20)
    private String oldPassword;

    @NotEmpty
    @Length(min = 8, max = 20)
    @Pattern(regexp = PASSWORD_PATTERN, message = MESSAGE)
    private String newPassword;

    @NotEmpty
    @Length(min = 8, max = 20)
    private String confirmPassword;

    public @NotEmpty @Length(min = 8, max = 20) String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(@NotEmpty @Length(min = 8, max = 20) String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public @NotEmpty @Length(min = 8, max = 20) @Pattern(regexp = PASSWORD_PATTERN, message = MESSAGE) String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotEmpty @Length(min = 8, max = 20) @Pattern(regexp = PASSWORD_PATTERN, message = MESSAGE) String newPassword) {
        this.newPassword = newPassword;
    }

    public @NotEmpty @Length(min = 8, max = 20) String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotEmpty @Length(min = 8, max = 20) String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
