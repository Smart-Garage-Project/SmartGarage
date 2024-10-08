package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.helpers.ServiceModelMapper;
import com.example.smartgarage.models.Part;
import com.example.smartgarage.models.ServiceModel;
import com.example.smartgarage.models.ServiceModelDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.CarService;
import com.example.smartgarage.services.contracts.ExchangeService;
import com.example.smartgarage.services.contracts.PartService;
import com.example.smartgarage.services.contracts.ServiceService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    private final ExchangeService exchangeService;

    private final ServiceModelMapper serviceModelMapper;

    private final AuthenticationHelper authenticationHelper;

    public ServiceMvcController(CarService carService, ServiceService serviceService,
                                PartService partService, ExchangeService exchangeService, ServiceModelMapper serviceModelMapper,
                                AuthenticationHelper authenticationHelper) {
        this.carService = carService;
        this.serviceService = serviceService;
        this.partService = partService;
        this.exchangeService = exchangeService;
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

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("service", service);
        model.addAttribute("car", service.getCar());
        model.addAttribute("parts", parts);
        session.setAttribute("serviceId", id);
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

        try {
            carService.getByLicensePlate(serviceModelDto.getLicensePlate());
        } catch (EntityNotFoundException e) {
            bindingResult.rejectValue("licensePlate", "error.licensePlate", e.getMessage());
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
                                      HttpSession session) {
        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        ServiceModel service = serviceService.getById(id);
        List<Integer> includedPartIds = service.getParts().stream().map(Part::getId).toList();
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

    @GetMapping
    public String getServiceDetails(Model model) {
        model.addAttribute("topCurrencies", exchangeService.getTopCurrencies());
        return "ServiceDetailsView";
    }

    @GetMapping("/convert")
    public String convertCurrency(@RequestParam double amount, @RequestParam String toCurrency,
                                  Model model, HttpSession session) {
        double convertedTotal = exchangeService.convertCurrency(amount, toCurrency);
        String formattedTotal = String.format("%.2f", convertedTotal);
        model.addAttribute("convertedTotal", formattedTotal + " " + toCurrency);

        User currentUser = authenticationHelper.tryGetCurrentUser(session);
        ServiceModel service = serviceService.getById((Integer) session.getAttribute("serviceId"));
        List<Part> parts = service.getParts();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("service", service);
        model.addAttribute("car", service.getCar());
        model.addAttribute("parts", parts);

        return "ServiceDetailsView";
    }
}
