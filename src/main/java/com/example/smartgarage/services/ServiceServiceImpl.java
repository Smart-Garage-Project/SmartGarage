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
    public ServiceModel create(ServiceModel service, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return serviceRepository.create(service);
    }

    @Override
    public void addPartsToService(int serviceId, List<Part> parts, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        ServiceModel service = serviceRepository.getById(serviceId);
        parts.forEach(part -> service.getParts().add(part));
        service.setTotal(calculateTotalPrice(service));
        serviceRepository.update(service);
    }

    @Override
    public double calculateTotalPrice(ServiceModel serviceModel) {
        double totalPartsPrice = serviceModel.getParts().stream().mapToDouble(Part::getPrice).sum();
        double total = 0;
        switch (serviceModel.getCar().getCarClass().getName()){
            case "Low":
                total = totalPartsPrice;
                break;

            case "Mid":
                total = 1.4 * totalPartsPrice;
                break;

            case "High":
                total = 1.8 * totalPartsPrice;
                break;

            case "Exotic":
                total = 2 * totalPartsPrice;
                break;
        }
        return total;
    }
}
