package com.example.smartgarage.controllers.MVC;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    public HomeMvcController() {
    }

    @GetMapping
    public String showHomePage() {
        return "HomeView";
    }

    @GetMapping("/about")
    public String about() {
        return "AboutView";
    }
}
