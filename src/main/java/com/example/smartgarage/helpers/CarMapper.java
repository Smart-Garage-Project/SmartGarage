package com.example.smartgarage.helpers;

import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.CarDto;
import com.example.smartgarage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    private final UserService userService;

    @Autowired
    public CarMapper(UserService userService) {
        this.userService = userService;
    }

    public Car fromDto(CarDto carDto) {
        Car car = new Car();
        car.setLicensePlate(carDto.getLicensePlate());
        car.setVin(carDto.getVin());
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setOwner(userService.getByUsername(carDto.getOwnerUsername()));
        return car;
    }
}
