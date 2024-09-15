package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.helpers.CarMapper;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.CarDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.CarRepository;
import com.example.smartgarage.services.contracts.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    private final CarMapper carMapper;

    private final CarRepository carRepository;

    private final AuthorizationHelper authorizationHelper;

    @Autowired
    public CarServiceImpl(CarMapper carMapper, AuthorizationHelper authorizationHelper, CarRepository carRepository) {
        this.carMapper = carMapper;
        this.authorizationHelper = authorizationHelper;
        this.carRepository = carRepository;
    }

    @Override
    public Car getByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new EntityNotFoundException("Car", "License Plate", licensePlate));
    }

    @Override
    public Page<Car> getAllCarsFiltered(User user, String brand, String model, String licensePlate, Pageable pageable) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return carRepository.findAllFiltered(brand, model, licensePlate, pageable);
    }

    @Override
    public Page<Car> getUserCars(int id, User user, Pageable pageable) {
        authorizationHelper.checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(user, id);
        return carRepository.findUserCars(id, pageable);
    }

    @Override
    public Car getById(int id, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car", id));
    }

    @Override
    public Car create(CarDto carDto, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);

        carRepository.findByLicensePlate(carDto.getLicensePlate()).ifPresent(car -> {
            throw new EntityDuplicateException("Car", "license plate", carDto.getLicensePlate());
        });

        carRepository.findByVin(carDto.getVin()).ifPresent(car -> {
            throw new EntityDuplicateException("Car", "vin", carDto.getVin());
        });

        Car car = carMapper.fromDto(carDto);

        return carRepository.save(car);
    }

    @Override
    public Car update(int id, Car car, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return carRepository.save(car);
    }
}
