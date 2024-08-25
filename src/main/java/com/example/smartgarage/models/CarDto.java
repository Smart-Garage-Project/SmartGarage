package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CarDto {
    @NotEmpty
    private String licensePlate;
    @NotEmpty
    private String vin;
    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @NotNull
    private int year;
    @NotEmpty
    private String ownerUsername;

    public CarDto() {

    }

    public @NotEmpty String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(@NotEmpty String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public @NotEmpty String getVin() {
        return vin;
    }

    public void setVin(@NotEmpty String vin) {
        this.vin = vin;
    }

    public @NotEmpty String getBrand() {
        return brand;
    }

    public void setBrand(@NotEmpty String brand) {
        this.brand = brand;
    }

    public @NotEmpty String getModel() {
        return model;
    }

    public void setModel(@NotEmpty String model) {
        this.model = model;
    }

    @NotNull
    public int getYear() {
        return year;
    }

    public void setYear(@NotNull int year) {
        this.year = year;
    }

    @NotEmpty
    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(@NotEmpty String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
