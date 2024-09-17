package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.services.ExchangeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchange")
public class RestExchangeController {

    private final ExchangeServiceImpl exchangeService;

    @Autowired
    public RestExchangeController(ExchangeServiceImpl exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping
    public double convertCurrency(@RequestParam double amount, @RequestParam String toCurrency) {
        return exchangeService.convertCurrency(amount, toCurrency);
    }
}