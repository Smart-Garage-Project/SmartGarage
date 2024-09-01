package com.example.smartgarage.services;

import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.models.Part;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.ServiceRepository;
import com.example.smartgarage.services.contracts.PartService;
import com.example.smartgarage.services.contracts.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    private final PartService partService;

    private final AuthorizationHelper authorizationHelper;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository, PartService partService, AuthorizationHelper authorizationHelper) {
        this.serviceRepository = serviceRepository;
        this.partService = partService;
        this.authorizationHelper = authorizationHelper;
    }

    @Override
    public ServiceModel getById(int id) {
        return serviceRepository.getById(id);
    }

    @Override
    public List<ServiceModel> getByCarId(int id) {
        return serviceRepository.getByCarId(id);
    }

    @Override
    public ServiceModel create(ServiceModel serviceModel, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return serviceRepository.create(serviceModel);
    }

    @Override
    public void addPartsToService(int serviceId, List<Part> parts, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        ServiceModel serviceModel = serviceRepository.getById(serviceId);
        parts.forEach(part -> serviceModel.getParts().add(part));
        serviceModel.setTotal(calculateTotalPrice(serviceModel));
        serviceRepository.update(serviceModel);
    }

    @Override
    public double calculateTotalPrice(ServiceModel serviceModel) {
        double totalPartsPrice = serviceModel.getParts().stream().mapToDouble(Part::getPrice).sum();
        double total = 0;
        switch (serviceModel.getCar().getCarClass().getName()){
            case "Low":
                total = totalPartsPrice + totalPartsPrice * 0.1;
                break;

            case "Mid":
                total = totalPartsPrice + totalPartsPrice * 0.2;
                break;

            case "High":
                total = totalPartsPrice + totalPartsPrice * 0.4;
                break;

            case "Exotic":
                total = totalPartsPrice + totalPartsPrice;
                break;
        }
        return total;
    }
}
