package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Model;
import com.example.smartgarage.models.User;

public interface ModelService {
    Model getByName(String name);
    Model create(Model model);
}
