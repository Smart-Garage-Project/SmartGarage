package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.CarClass;

public interface CarClassService {
    CarClass getByName(String name);
}
