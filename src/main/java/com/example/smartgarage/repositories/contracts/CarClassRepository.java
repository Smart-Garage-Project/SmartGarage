package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.CarClass;

public interface CarClassRepository {
    CarClass getByName(String name);
}
