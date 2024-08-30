package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.Brand;

public interface BrandRepository {
    Brand getByName(String name);
    Brand create(Brand brand);
}
