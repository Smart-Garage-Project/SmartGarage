package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.CarRepository;
import com.example.smartgarage.services.contracts.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final AuthorizationHelper authorizationHelper;

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(AuthorizationHelper authorizationHelper, CarRepository carRepository) {
        this.authorizationHelper = authorizationHelper;
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getCars(User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return carRepository.findUserCars(user.getId());
    }

    @Override
    public Car getById(int id, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car", id));
    }

    @Override
    public Car create(Car car, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return carRepository.save(car);
    }

    @Override
    public Car update(int id, Car car, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return carRepository.save(car);
    }
}
