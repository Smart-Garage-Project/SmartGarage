package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.models.Part;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.ServiceRepository;
import com.example.smartgarage.services.contracts.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    private final AuthorizationHelper authorizationHelper;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository, AuthorizationHelper authorizationHelper) {
        this.serviceRepository = serviceRepository;
        this.authorizationHelper = authorizationHelper;
    }

    @Override
    public ServiceModel getById(int id) {
        return serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service", id));
    }

    @Override
    public Page<ServiceModel> getByCarId(int carId, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        return serviceRepository.findCarServices(carId, fromDate, toDate, pageable);
    }

    @Override
    public ServiceModel create(ServiceModel service, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        return serviceRepository.save(service);
    }

    @Override
    public void addPartsToService(int serviceId, List<Part> parts, User user) {
        authorizationHelper.checkIfCurrentUserIsEmployee(user);
        ServiceModel service = getById(serviceId);
        parts.forEach(part -> service.getParts().add(part));
        service.setTotal(calculateTotalPrice(service));
        serviceRepository.save(service);
    }

    @Override
    public double calculateTotalPrice(ServiceModel serviceModel) {
        double totalPartsPrice = serviceModel.getParts().stream().mapToDouble(Part::getPrice).sum();
        double total = 0;
        switch (serviceModel.getCar().getModel().getCarClass().getName()){
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
