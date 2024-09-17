package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.ArgumentsMismatchException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.exceptions.InvalidYearException;
import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.helpers.CarMapper;
import com.example.smartgarage.models.*;
import com.example.smartgarage.repositories.contracts.CarRepository;
import com.example.smartgarage.services.contracts.BrandService;
import com.example.smartgarage.services.contracts.CarService;
import com.example.smartgarage.services.contracts.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class CarServiceImpl implements CarService {

    private final CarMapper carMapper;

    private final BrandService brandService;

    private final ModelService modelService;

    private final CarRepository carRepository;

    private final AuthorizationHelper authorizationHelper;

    @Autowired
    public CarServiceImpl(CarMapper carMapper, BrandService brandService, ModelService modelService, AuthorizationHelper authorizationHelper, CarRepository carRepository) {
        this.carMapper = carMapper;
        this.brandService = brandService;
        this.modelService = modelService;
        this.authorizationHelper = authorizationHelper;
        this.carRepository = carRepository;
    }

    @Override
    public Car getByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new EntityNotFoundException("Car", "License Plate", licensePlate));
    }

    @Override
    public Page<Car> getAllCarsFiltered(User user, String ownerUsername, String licensePlate,
                                        String brand, String model, Pageable pageable) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return carRepository.findAllFiltered(ownerUsername, licensePlate, brand, model, pageable);
    }

    @Override
    public Page<Car> getUserCars(int id, User user, Pageable pageable) {
        authorizationHelper.checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(user, id);
        return carRepository.findUserCars(id, pageable);
    }

    @Override
    public Car getById(int id, User user) {
//        authorizationHelper.checkIfCurrentUserIsEmployeeOrIsSameAsTargetUser(user, id);
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car", id));
    }

    @Override
    public Car create(CarDto carDto, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);

        carRepository.findByLicensePlate(carDto.getLicensePlate()).ifPresent(car -> {
            throw new EntityDuplicateException("Car", "license plate", carDto.getLicensePlate());
        });

        Brand brand;

        try {
           brand  = brandService.getByName(carDto.getBrandName());
        }catch (EntityNotFoundException e){
            brand = new Brand();
            brand.setName(carDto.getBrandName());
            brandService.create(brand);
        }

        Model model;

        try {
            model = modelService.getByName(carDto.getModelName());
        }catch (EntityNotFoundException e){
            model = new Model();
            model.setName(carDto.getModelName());
            model.setBrand(brand);
            modelService.create(model);
        }

        if (carDto.getYear() < 2000 || carDto.getYear() > Year.now().getValue()) {
            throw new InvalidYearException(2000, Year.now().getValue());
        }

        Car car = carMapper.fromDto(carDto);

        return carRepository.save(car);
    }

    @Override
    public Car update(int id, Car car, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return carRepository.save(car);
    }
}
