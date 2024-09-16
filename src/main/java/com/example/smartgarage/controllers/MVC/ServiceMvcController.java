package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.helpers.ServiceModelMapper;
import com.example.smartgarage.models.*;
import com.example.smartgarage.services.contracts.CarService;
import com.example.smartgarage.services.contracts.PartService;
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

import java.util.List;

@Controller
@RequestMapping("/services")
public class ServiceMvcController {

    private final CarService carService;

    private final PartService partService;

    private final ServiceService serviceService;

    private final ServiceModelMapper serviceModelMapper;

    private final AuthenticationHelper authenticationHelper;

    public ServiceMvcController(CarService carService, ServiceService serviceService, PartService partService, ServiceModelMapper serviceModelMapper, AuthenticationHelper authenticationHelper) {
        this.carService = carService;
        this.serviceService = serviceService;
        this.partService = partService;
        this.serviceModelMapper = serviceModelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/{id}")
    public String showServiceDetails(@PathVariable int id, Model model, HttpSession session) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        ServiceModel service = serviceService.getById(id);
        List<Part> parts = service.getParts();

        model.addAttribute("service", service);
        model.addAttribute("car", service.getCar());
        model.addAttribute("parts", parts);
        return "ServiceDetailsView";
    }

    @GetMapping("/create")
    public String showCreateServiceForm(Model model) {
        model.addAttribute("newService", new ServiceModelDto());

        return "CreateServiceView";
    }

    @PostMapping("/create")
    public String createService(@Valid @ModelAttribute("newService") ServiceModelDto serviceModelDto,
                                BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "CreateServiceView";
        }

        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        Car car = carService.getByLicensePlate(serviceModelDto.getLicensePlate());
        if (car == null) {
            bindingResult.rejectValue("licensePlate", "error.newService",
                    "Car not found with the given license plate");
            return "CreateServiceView";
        }

        ServiceModel serviceModel = serviceModelMapper.fromDto(serviceModelDto, currentUser);

        ServiceModel createdService = serviceService.create(serviceModel, currentUser);

        List<Part> parts = partService.getAllParts();
        model.addAttribute("newService", createdService);
        model.addAttribute("serviceCreated", true);
        model.addAttribute("parts", parts);

        return "CreateServiceView";
    }

    @GetMapping("/{id}/edit")
    public String showEditServiceForm(@PathVariable int id, Model model,
                                      HttpSession session, @RequestParam(defaultValue = "0") int page) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        ServiceModel service = serviceService.getById(id);
        List<Integer> includedPartIds = service.getParts().stream().map(Part::getId).toList();
        Pageable pageable = PageRequest.of(page, 10);
        List<Part> excludedParts = partService.getExcludedParts(includedPartIds);

        model.addAttribute("isEmployee", currentUser.isEmployee());
        model.addAttribute("service", service);
        model.addAttribute("excludedParts", excludedParts);

        return "EditServiceView";
    }

    @PostMapping("/{id}/edit")
    public String editService(@PathVariable int id, @RequestParam List<Integer> partIds, HttpSession session) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        List<Part> parts = partIds.stream().map(partService::getById).toList();
        serviceService.addPartsToService(id, parts, currentUser);
        return "redirect:/services/" + id;
    }
}
