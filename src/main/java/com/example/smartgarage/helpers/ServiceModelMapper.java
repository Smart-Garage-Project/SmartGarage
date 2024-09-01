package com.example.smartgarage.helpers;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.models.Part;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.models.ServiceModelDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.CarService;
import com.example.smartgarage.services.contracts.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceModelMapper {
    private final CarService carService;
    private final PartService partService;
    @Autowired
    public ServiceModelMapper(CarService carService, PartService partService) {
        this.carService = carService;
        this.partService = partService;
    }

    public ServiceModel fromDto(ServiceModelDto serviceModelDto, User user) {
        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setCar(carService.getById(serviceModelDto.getCarId(), user));
        return serviceModel;
    }
}
