package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Brand;
import com.example.smartgarage.models.User;

public interface BrandService {
    Brand getByName(String name);
    Brand create(Brand brand);
}
