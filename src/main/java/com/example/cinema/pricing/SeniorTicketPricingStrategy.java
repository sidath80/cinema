package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.springframework.stereotype.Component;

@Component
public class SeniorTicketPricingStrategy extends AbstractTicketPricingStrategy {
    protected SeniorTicketPricingStrategy(TicketPricingProperties pricing) {
        super(pricing);
    }

    @Override
    public double calculateCost(int qty) {
        return qty * pricing.getAdult() * (1 - pricing.getSeniorDiscount());
    }

    @Override
    public TicketType getSupportedType() {
        return TicketType.SENIOR;
    }
}