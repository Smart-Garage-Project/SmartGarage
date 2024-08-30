package com.example.smartgarage.services;

import com.example.smartgarage.helpers.AuthorizationHelper;
import com.example.smartgarage.models.Brand;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.contracts.BrandRepository;
import com.example.smartgarage.services.contracts.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final AuthorizationHelper authorizationHelper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, AuthorizationHelper authorizationHelper) {
        this.brandRepository = brandRepository;
        this.authorizationHelper = authorizationHelper;
    }

    @Override
    public Brand getByName(String name) {
        return brandRepository.getByName(name);
    }

    @Override
    public Brand create(Brand brand) {
        return brandRepository.create(brand);
    }
}
