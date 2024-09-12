package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.models.Part;
import com.example.smartgarage.services.contracts.PartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parts")
public class RestPartController {

    private final PartService partService;

    public RestPartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping()
    public Page<Part> getAllParts(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return partService.getParts(pageable);
    }
}
