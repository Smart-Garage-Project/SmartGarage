package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartService {

    Page<Part> getParts(Pageable pageable);

    Page<Part> getExcludedParts(List<Integer> excludedPartIds, Pageable pageable);

    Part getById(int id);
}
