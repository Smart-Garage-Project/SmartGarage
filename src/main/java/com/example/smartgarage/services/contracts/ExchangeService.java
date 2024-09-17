package com.example.smartgarage.services.contracts;

import java.util.List;

public interface ExchangeService {

    double convertCurrency(double amount, String toCurrency);

    List<String> getTopCurrencies();
}
