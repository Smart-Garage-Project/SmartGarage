package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Part;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceService {

    ServiceModel getById(int id);

    Page<ServiceModel> getByCarId(int id, Pageable pageable);

    ServiceModel create(ServiceModel serviceModel, User user);

    void addPartsToService(int serviceId, List<Part> parts, User user);

    double calculateTotalPrice(ServiceModel serviceModel);
}
