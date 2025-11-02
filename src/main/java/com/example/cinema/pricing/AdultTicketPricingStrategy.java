package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.springframework.stereotype.Component;

@Component
public class AdultTicketPricingStrategy extends AbstractTicketPricingStrategy {
    protected AdultTicketPricingStrategy(TicketPricingProperties pricing) {
        super(pricing);
    }

    @Override
    public double calculateCost(int qty) {
        return qty * pricing.getAdult();
    }

    @Override
    public TicketType getSupportedType() {
        return TicketType.ADULT;
    }
}