package com.example.smartgarage.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRateResponse {
    private Map<String, Double> conversion_rates;
}