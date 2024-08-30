package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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

    public @NotEmpty String getBrandName() {
        return brandName;
    }

    public void setBrandName(@NotEmpty String brandName) {
        this.brandName = brandName;
    }

    public @NotEmpty String getModelName() {
        return modelName;
    }

    public void setModelName(@NotEmpty String modelName) {
        this.modelName = modelName;
    }
}
