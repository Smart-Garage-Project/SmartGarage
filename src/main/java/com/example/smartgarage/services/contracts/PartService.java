package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartService {

    List<Part> getAllParts();

    Page<Part> getParts(Pageable pageable);

    List<Part> getExcludedParts(List<Integer> excludedPartIds);

    Part getById(int id);
}
