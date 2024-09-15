package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.Car;
import com.example.smartgarage.models.CarDto;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.CarService;
import com.example.smartgarage.services.contracts.ServiceService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/cars")
public class CarMvcController {

    private final CarService carService;

    private final ServiceService serviceService;

    private final AuthenticationHelper authenticationHelper;

    public CarMvcController(CarService carService, ServiceService serviceService,
                            AuthenticationHelper authenticationHelper) {
        this.carService = carService;
        this.serviceService = serviceService;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String showAllCars(Model model, HttpSession session,
                              @RequestParam(required = false) String ownerUsername,
                              @RequestParam(required = false) String licensePlate,
                              @RequestParam(required = false) String brand,
                              @RequestParam(required = false) String carModel,
                              @RequestParam(defaultValue = "0") int page) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        Pageable pageable = PageRequest.of(page, 4);
        Page<Car> carsPage = carService.getAllCarsFiltered(currentUser, ownerUsername,
                licensePlate, brand, carModel, pageable);
        model.addAttribute("carsPage", carsPage);
        return "AllCarsView";
    }

    @GetMapping("/{carId}")
    public String showCarDetails(@PathVariable int carId,
                                 @RequestParam(required = false) LocalDate fromDate,
                                 @RequestParam(required = false) LocalDate toDate,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model, HttpSession session) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        Car car = carService.getById(carId, currentUser);
        model.addAttribute("car", car);
        Pageable pageable = PageRequest.of(page, 10);
        Page<ServiceModel> serviceModelPage = serviceService.getByCarId(carId, fromDate, toDate, pageable);
        model.addAttribute("serviceModelPage", serviceModelPage);
        return "CarDetailsView";
    }

    @GetMapping("/create")
    public String showCreateCarPage(Model model) {
        model.addAttribute("newCar", new CarDto());
        return "CreateCarView";
    }

    @PostMapping("/create")
    public String handleCreateCar(@Valid @ModelAttribute("newCar") CarDto newCarDto,
                                  BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "CreateCarView";
        }

        try {
            User currentUser = authenticationHelper.tryGetCurrentUser(session);
            carService.create(newCarDto, currentUser);
            return "redirect:/employee-panel";
        } catch (EntityDuplicateException e) {
            bindingResult.rejectValue("licensePlate", "error.licensePlate", e.getMessage());
            bindingResult.rejectValue("vin", "error.vin", e.getMessage());
            return "CreateCarView";
        }
    }
}
