package com.example.smartgarage.controllers.Rest;

import com.example.smartgarage.services.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RestCurrencyController {

    @Autowired
    private CurrencyServiceImpl currencyService;

    @GetMapping("/exchange-rates")
    public Map<String, Double> getExchangeRates(@RequestParam String baseCurrency) {
        return currencyService.getLiveExchangeRates(baseCurrency);
    }
}
