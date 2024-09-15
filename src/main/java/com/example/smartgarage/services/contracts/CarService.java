package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.CarDto;
import com.example.smartgarage.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    Car getByLicensePlate(String licensePlate);

    Page<Car> getAllCarsFiltered(User user, String ownerUsername, String licensePlate,
                                 String brand, String model, Pageable pageable);

    Page<Car> getUserCars(int id, User user, Pageable pageable);

    Car getById(int id, User user);

    Car create(CarDto carDto, User user);

    Car update(int id, Car car, User user);
}
