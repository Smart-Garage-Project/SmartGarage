package com.example.smartgarage.helpers;

import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.CarDto;
import com.example.smartgarage.services.contracts.BrandService;
import com.example.smartgarage.services.contracts.CarClassService;
import com.example.smartgarage.services.contracts.ModelService;
import com.example.smartgarage.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    private final UserService userService;
    private final ModelService modelService;
    private final BrandService brandService;

    @Autowired
    public CarMapper(UserService userService, ModelService modelService, BrandService brandService, CarClassService carClassService) {
        this.userService = userService;
        this.modelService = modelService;
        this.brandService = brandService;
    }

    public Car fromDto(CarDto carDto) {
        Car car = new Car();
        car.setLicensePlate(carDto.getLicensePlate());
        car.setVin(carDto.getVin());
        car.setYear(carDto.getYear());
        car.setOwner(userService.getByUsername(carDto.getOwnerUsername()));
        car.setBrand(brandService.getByName(carDto.getBrandName()));
        car.setModel(modelService.getByName(carDto.getModelName()));

        return car;
    }
}
