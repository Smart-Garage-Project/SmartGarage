package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {

    private final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[+\\-*^])[A-Za-z\\d+\\-*^]{8,}$";

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
}
