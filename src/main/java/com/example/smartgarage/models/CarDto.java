package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarDto {

    @NotEmpty(message = "License plate is required")
    @Pattern(regexp = "^[A-Z]{1,2}\\s?[0-9]{4}\\s?[A-Z]{1,2}$", message = "Invalid license plate format")
    private String licensePlate;

    @NotEmpty
    private String vin;

    @NotNull
    private int year;

    @NotEmpty
    private String ownerUsername;

    @NotEmpty
    private String brandName;

    @NotEmpty
    private String modelName;
}
