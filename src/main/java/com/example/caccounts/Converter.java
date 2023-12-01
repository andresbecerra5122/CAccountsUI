package com.example.caccounts;

import java.util.HashMap;
import java.util.Map;

class Converter {
    private Map<String, Double> exchangeRates;

    public Converter() {
        // Exchange rates for demonstration purposes
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);  // Default: USD as base currency
        exchangeRates.put("EUR", 0.85); // Example exchange rate for EUR
        exchangeRates.put("JPY", 110.0); // Example exchange rate for JPY
    }

    public double convert(double amount, String fromCurrency, String toCurrency) {
        if (!exchangeRates.containsKey(fromCurrency) || !exchangeRates.containsKey(toCurrency)) {
            System.out.println("Invalid currency.");
            return 0.0;
        }

        double conversionRate = exchangeRates.get(toCurrency) / exchangeRates.get(fromCurrency);
        return amount * conversionRate;
    }
}