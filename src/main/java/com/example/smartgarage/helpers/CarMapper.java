package com.example.smartgarage.helpers;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Brand;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.CarDto;
import com.example.smartgarage.models.Model;
import com.example.smartgarage.services.contracts.BrandService;
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
    public CarMapper(UserService userService, ModelService modelService, BrandService brandService) {
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
        try {
            Brand brand = brandService.getByName(carDto.getBrandName());
            car.setBrand(brand);
        }catch (EntityNotFoundException e){
            Brand brand = new Brand();
            brand.setName(carDto.getBrandName());
            car.setBrand(brandService.create(brand));
        }
        try {
            Model model = modelService.getByName(carDto.getModelName());
            car.setModel(model);
        }catch (EntityNotFoundException e){
            Model model = new Model();
            model.setName(carDto.getModelName());
            model.setBrand(car.getBrand());
            car.setModel(modelService.create(model));
        }
        return car;
    }
}
