package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Part;
import com.example.smartgarage.repositories.contracts.PartRepository;
import com.example.smartgarage.services.contracts.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Part> getParts(Pageable pageable) {
        return partRepository.findAllPaged(pageable);
    }

    @Override
    public Page<Part> getExcludedParts(List<Integer> excludedPartIds, Pageable pageable) {
        return partRepository.findAllExcluded(excludedPartIds, pageable);
    }


    @Override
    public Part getById(int id) {
        return partRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Part", id));
    }
}
