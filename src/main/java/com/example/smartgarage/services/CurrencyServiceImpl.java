package com.example.smartgarage.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@Service
public class CurrencyServiceImpl {
    private static final String API_KEY = "0d61449a5e15b62da49ba3dc";
    private static final String API_URL = "https://api.exchangeratesapi.io/latest?access_key=" + API_KEY + "&base=";

    public Map<String, Double> getLiveExchangeRates(String baseCurrency) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + baseCurrency;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (Map<String, Double>) response.get("rates");
    }

    public Map<String, Double> convertBGNToOtherCurrencies(double amountInBGN) {
        Map<String, Double> exchangeRates = getLiveExchangeRates("BGN");
        Map<String, Double> convertedAmounts = new HashMap<>();
        for (Map.Entry<String, Double> entry : exchangeRates.entrySet()) {
            convertedAmounts.put(entry.getKey(), amountInBGN * entry.getValue());
        }
        return convertedAmounts;
    }
}
