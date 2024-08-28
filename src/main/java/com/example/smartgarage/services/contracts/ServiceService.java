package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Part;
import com.example.smartgarage.models.ServiceModel;

import java.util.List;

public interface ServiceService {

    ServiceModel getById(int id);

    List<ServiceModel> getByCarId(int id);

    ServiceModel create(ServiceModel serviceModel);

    void addPartToService(int serviceId, List<Part> parts);
}
