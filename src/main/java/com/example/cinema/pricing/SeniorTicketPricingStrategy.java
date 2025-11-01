package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.springframework.stereotype.Component;

@Component
public class SeniorTicketPricingStrategy implements TicketPricingStrategy {
    @Override
    public double calculateCost(int qty, TicketPricingProperties pricing) {
        return qty * pricing.getAdult() * (1 - pricing.getSeniorDiscount());
    }

    @Override
    public TicketType getSupportedType() {
        return TicketType.SENIOR;
    }
}