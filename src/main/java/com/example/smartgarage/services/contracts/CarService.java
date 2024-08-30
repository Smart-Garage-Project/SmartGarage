package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.User;

import java.util.List;

public interface CarService {

    List<Car> getCars(User user);

    Car getById(int id, User user);

    Car create(Car car, User user);

    Car update(int id, Car car, User user);
}