package com.example.smartgarage.repositories;

import com.example.smartgarage.models.Car;

import java.util.List;

public interface CarRepository {

    List<Car> getUserCars(int userId);

    Car create(Car car);

    void update(Car car);

    List<Car> getCars();

    Car getById(int id);
}
