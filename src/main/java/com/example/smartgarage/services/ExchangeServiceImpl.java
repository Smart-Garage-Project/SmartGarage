package com.example.smartgarage.services;

import com.example.smartgarage.models.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeServiceImpl {

    @Value("${exchangerate.api.base-url}")
    private String baseUrl;

    @Value("${exchangerate.api.key}")
    private String apiKey;

    @Value("${exchangerate.base.currency}")
    private String baseCurrency;

    private final RestTemplate restTemplate;

    public ExchangeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRateResponse getExchangeRates() {
        String url = String.format("%s/%s/latest/%s", baseUrl, apiKey, baseCurrency);
        return restTemplate.getForObject(url, ExchangeRateResponse.class);
    }

    public double convertCurrency(double amount, String toCurrency) {
        ExchangeRateResponse response = getExchangeRates();
        if (response == null || response.getConversion_rates() == null) {
            throw new IllegalStateException("Failed to retrieve exchange rates");
        }
        Double rate = response.getConversion_rates().get(toCurrency);
        if (rate == null) {
            throw new IllegalArgumentException("Invalid target currency: " + toCurrency);
        }
        return amount * rate;
    }

    public List<String> getTopCurrencies() {
        ExchangeRateResponse response = getExchangeRates();
        if (response == null || response.getConversion_rates() == null) {
            throw new IllegalStateException("Failed to retrieve exchange rates");
        }
        return response.getConversion_rates().keySet().stream()
                .limit(5)
                .collect(Collectors.toList());
    }
}