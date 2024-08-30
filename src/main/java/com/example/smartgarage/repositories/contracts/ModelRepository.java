package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.Model;

public interface ModelRepository {
    Model getByName(String name);
    Model create(Model model);
}
