package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.Part;

import java.util.List;

public interface PartRepository {

    Part getById(int id);

    List<Part> getAll();

    List<Part> getAll(List<Part> excludedParts);

    List<Part> getListOfParts(List<Integer> partsIds);
}
