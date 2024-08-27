package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.helpers.CarMapper;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.CarDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class RestCarController {

    private final CarService carService;

    private final AuthenticationHelper authenticationHelper;

    private final CarMapper carMapper;

    @Autowired
    public RestCarController(CarService carService, AuthenticationHelper authenticationHelper, CarMapper carMapper) {
        this.carService = carService;
        this.authenticationHelper = authenticationHelper;
        this.carMapper = carMapper;
    }

    @GetMapping
    public List<Car> getCars(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.getCars(user);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Car getCarById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.getById(id, user);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Car create(@RequestHeader HttpHeaders headers,@Valid @RequestBody CarDto carDto){
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Car car = carMapper.fromDto(carDto);
            return carService.create(car, user);
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Car update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody CarDto carDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Car car = carMapper.fromDto(carDto);
            return carService.update(id, car, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
