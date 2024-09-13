package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarDto {

    @NotEmpty
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

    @NotEmpty
    private String carClassName;
}
