package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.helpers.ServiceModelMapper;
import com.example.smartgarage.models.Part;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.models.ServiceModelDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.PartService;
import com.example.smartgarage.services.contracts.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
public class RestServiceController {

    private final ServiceService serviceService;

    private final PartService partService;

    private final AuthenticationHelper authenticationHelper;

    private final ServiceModelMapper serviceModelMapper;

    @Autowired
    public RestServiceController(ServiceService serviceService, PartService partService, AuthenticationHelper authenticationHelper, ServiceModelMapper serviceModelMapper) {
        this.serviceService = serviceService;
        this.partService = partService;
        this.authenticationHelper = authenticationHelper;
        this.serviceModelMapper = serviceModelMapper;
    }

    @GetMapping("/{id}")
    public ServiceModel getServiceById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        return serviceService.getById(id);
    }

    @PostMapping
    public ServiceModel create(@RequestHeader HttpHeaders headers, @RequestBody ServiceModelDto serviceModelDto) {
        User user = authenticationHelper.tryGetUser(headers);
        ServiceModel serviceModel = serviceModelMapper.fromDto(serviceModelDto, user);
        return serviceService.create(serviceModel, user);
    }

    @PostMapping("/{id}/parts")
    public ServiceModel addPartsToService(@RequestHeader HttpHeaders headers, @PathVariable int id, @RequestBody List<Integer> partIds) {
        User user = authenticationHelper.tryGetUser(headers);
        List<Part> parts = partIds.stream().map(partService::getById).collect(Collectors.toList());
        serviceService.addPartsToService(id, parts, user);
        return serviceService.getById(id);
    }
}
