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
    public void addPartsToService(int serviceId, List<Part> parts) {
        ServiceModel serviceModel = serviceRepository.getById(serviceId);
        for (Part part : parts) {
            serviceModel.getParts().add(part);
        }
        serviceRepository.update(serviceModel);
    }
}
