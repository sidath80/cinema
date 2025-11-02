package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.springframework.stereotype.Component;

@Component
public class TeenTicketPricingStrategy extends AbstractTicketPricingStrategy {
    protected TeenTicketPricingStrategy(TicketPricingProperties pricing) {
        super(pricing);
    }

    @Override
    public double calculateCost(int qty) {
        return qty * pricing.getTeen();
    }

    @Override
    public TicketType getSupportedType() {
        return TicketType.TEEN;
    }
}