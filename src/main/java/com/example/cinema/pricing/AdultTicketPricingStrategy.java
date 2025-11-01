package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.springframework.stereotype.Component;

@Component
public class AdultTicketPricingStrategy implements TicketPricingStrategy {
    @Override
    public double calculateCost(int qty, TicketPricingProperties pricing) {
        return qty * pricing.getAdult();
    }

    @Override
    public TicketType getSupportedType() {
        return TicketType.ADULT;
    }
}