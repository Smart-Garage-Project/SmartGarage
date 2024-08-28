package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Part;

import java.util.List;

public interface PartService {

    List<Part> getParts();

    Part getById(int id);
}
