package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.springframework.stereotype.Component;

@Component
public class TeenTicketPricingStrategy implements TicketPricingStrategy {
    @Override
    public double calculateCost(int qty, TicketPricingProperties pricing) {
        return qty * pricing.getTeen();
    }

    @Override
    public TicketType getSupportedType() {
        return TicketType.TEEN;
    }
}