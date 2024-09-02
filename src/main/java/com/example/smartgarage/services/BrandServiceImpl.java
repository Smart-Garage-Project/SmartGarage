package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Brand;
import com.example.smartgarage.repositories.contracts.BrandRepository;
import com.example.smartgarage.services.contracts.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand getByName(String name) {
        return brandRepository.findByName(name).
                orElseThrow(() -> new EntityNotFoundException("Brand", "name", name));
    }

    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }
}
