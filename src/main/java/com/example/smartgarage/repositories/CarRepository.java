package com.example.smartgarage.repositories;

import com.example.smartgarage.models.Car;

import java.util.List;

public interface CarRepository {

    List<Car> getUserCars(int userId);

    void create(Car car);

    void update(Car car);

    List<Car> getCars();

    Car getById(int id);
}
