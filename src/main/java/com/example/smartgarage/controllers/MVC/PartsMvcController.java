package com.example.smartgarage.controllers.MVC;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.Part;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.contracts.PartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/parts")
public class PartsMvcController {

    private final PartService partService;

    private final AuthenticationHelper authenticationHelper;

    public PartsMvcController(PartService partService, AuthenticationHelper authenticationHelper) {
        this.partService = partService;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping()
    public String showAllParts(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 3);
        Page<Part> partsPage = partService.getParts(pageable);
        model.addAttribute("partsPage", partsPage);

        try {
            User currentUser = authenticationHelper.tryGetCurrentUser(session);
            model.addAttribute("currentUser", currentUser);
        } catch (AuthorizationException e) {
            return "WhatWeOfferView";
        }

        return "WhatWeOfferView";
    }
}
