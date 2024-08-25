package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class RestCarController {
    private final CarService carService;
    private final AuthenticationHelper authenticationHelper;
    @Autowired
    public RestCarController(CarService carService, AuthenticationHelper authenticationHelper) {
        this.carService = carService;
        this.authenticationHelper = authenticationHelper;
    }
    @GetMapping
    public List<Car> getCars(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.getCars(user);
        }catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
