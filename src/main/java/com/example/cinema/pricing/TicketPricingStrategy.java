package com.example.cinema.pricing;

public interface TicketPricingStrategy {
    double calculateCost(int qty);

    TicketType getSupportedType();
}