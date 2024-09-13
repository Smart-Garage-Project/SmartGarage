package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

    @NotEmpty
    @Length(min = 2, max = 20)
    private String username;

    @NotEmpty
    @Length(min = 8, max = 20)
    private String password;
}
