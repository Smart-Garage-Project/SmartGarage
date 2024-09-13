package com.example.smartgarage.helpers;

import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.models.ServiceModelDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceModelMapper {

    private final CarService carService;

    @Autowired
    public ServiceModelMapper(CarService carService) {
        this.carService = carService;
    }

    public ServiceModel fromDto(ServiceModelDto serviceModelDto, User user) {
        ServiceModel serviceModel = new ServiceModel();
        Car car = carService.getByLicensePlate(serviceModelDto.getLicensePlate());
        serviceModel.setCar(car);
        serviceModel.setUser(car.getOwner());
        serviceModel.setLicensePlate(car.getLicensePlate());
        return serviceModel;
    }
}
