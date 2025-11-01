package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;

public interface TicketPricingStrategy {
    double calculateCost(int qty, TicketPricingProperties pricing);
    TicketType getSupportedType();
}