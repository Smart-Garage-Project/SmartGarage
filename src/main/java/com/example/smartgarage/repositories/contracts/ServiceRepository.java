package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.ServiceModel;

import java.util.List;

public interface ServiceRepository {

    ServiceModel getById(int id);

    List<ServiceModel> getByCarId(int id);

    ServiceModel create(ServiceModel serviceModel);

    ServiceModel update(ServiceModel serviceModel);
}
