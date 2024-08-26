package com.example.smartgarage.models;

import com.example.smartgarage.models.enums.CarClass;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "vin")
    private String vin;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_class")
    private CarClass carClass;

    public Car() {

    }

    public Car(int id, String licensePlate, String vin, String brand, String model,
               int year,
               User owner,
               CarClass carClass) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.owner = owner;
        this.carClass = carClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && year == car.year && Objects.equals(licensePlate, car.licensePlate)
                && Objects.equals(vin, car.vin) && Objects.equals(brand, car.brand)
                && Objects.equals(model, car.model) && Objects.equals(owner, car.owner)
                && carClass == car.carClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, licensePlate, vin, brand, model, year, owner, carClass);
    }
}
