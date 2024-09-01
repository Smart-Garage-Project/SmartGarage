package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return serviceService.getById(id);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ServiceModel create(@RequestHeader HttpHeaders headers, @RequestBody ServiceModelDto serviceModelDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            ServiceModel serviceModel = serviceModelMapper.fromDto(serviceModelDto, user);
            return serviceService.create(serviceModel, user);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/{id}/parts")
    public ServiceModel addPartsToService(@RequestHeader HttpHeaders headers, @PathVariable int id, @RequestBody List<Integer> partIds) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            List<Part> parts = partIds.stream().map(partService::getById).collect(Collectors.toList());
            serviceService.addPartsToService(id, parts, user);
            return serviceService.getById(id);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
