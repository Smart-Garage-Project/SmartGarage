package com.example.smartgarage.services;

import com.example.smartgarage.models.Part;
import com.example.smartgarage.repositories.contracts.PartRepository;
import com.example.smartgarage.services.contracts.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    @Autowired
    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public List<Part> getParts() {
        return partRepository.getAll();
    }

    @Override
    public Part getById(int id) {
        return partRepository.getById(id);
    }
}
