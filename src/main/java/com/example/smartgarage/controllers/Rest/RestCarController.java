package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.helpers.CarMapper;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.CarDto;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.CarService;
import com.example.smartgarage.services.contracts.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cars")
public class RestCarController {

    private final CarService carService;

    private final ServiceService serviceService;

    private final AuthenticationHelper authenticationHelper;

    private final CarMapper carMapper;

    @Autowired
    public RestCarController(CarService carService, ServiceService serviceService, AuthenticationHelper authenticationHelper, CarMapper carMapper) {
        this.carService = carService;
        this.serviceService = serviceService;
        this.authenticationHelper = authenticationHelper;
        this.carMapper = carMapper;
    }

    @GetMapping("/{id}")
    public Car getCarById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        return carService.getById(id, user);
    }

    @PostMapping
    public Car create(@RequestHeader HttpHeaders headers, @Valid @RequestBody CarDto carDto) {
        User user = authenticationHelper.tryGetUser(headers);
        return carService.create(carDto, user);
    }

    @PutMapping("/{id}")
    public Car update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody CarDto carDto) {
        User user = authenticationHelper.tryGetUser(headers);
        Car car = carMapper.fromDto(carDto);
        return carService.update(id, car, user);
    }

    @GetMapping("/{id}/services")
    public Page<ServiceModel> getCarServices(@RequestHeader HttpHeaders headers, @PathVariable int id,
                                             @RequestParam(required = false) LocalDate fromDate,
                                                @RequestParam(required = false) LocalDate toDate,
                                             @RequestParam(defaultValue = "0") int page) {
        User user = authenticationHelper.tryGetUser(headers);
        Pageable pageable = PageRequest.of(page, 10);
        return serviceService.getByCarId(id, fromDate, toDate, pageable);
    }
}
