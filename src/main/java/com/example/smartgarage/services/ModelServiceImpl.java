package com.example.smartgarage.services;

import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.models.Model;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.ModelRepository;
import com.example.smartgarage.services.contracts.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    private final AuthorizationHelper authorizationHelper;

    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository, AuthorizationHelper authorizationHelper) {
        this.modelRepository = modelRepository;
        this.authorizationHelper = authorizationHelper;
    }

    @Override
    public Model getByName(String name) {
        return modelRepository.getByName(name);
    }

    @Override
    public Model create(Model model) {
        return modelRepository.create(model);
    }
}
