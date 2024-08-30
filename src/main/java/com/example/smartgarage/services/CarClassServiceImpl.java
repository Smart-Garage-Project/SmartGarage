package com.example.smartgarage.services;

import com.example.smartgarage.models.CarClass;
import com.example.smartgarage.repositories.contracts.CarClassRepository;
import com.example.smartgarage.services.contracts.CarClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarClassServiceImpl implements CarClassService {
    private final CarClassRepository carClassRepository;

    @Autowired
    public CarClassServiceImpl(CarClassRepository carClassRepository) {
        this.carClassRepository = carClassRepository;
    }

    @Override
    public CarClass getByName(String name) {
        return carClassRepository.getByName(name);
    }
}
