package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarService {

    Page<Car> getUserCars(int id, User user, Pageable pageable);

    Car getById(int id, User user);

    Car create(Car car, User user);

    Car update(int id, Car car, User user);
}
