package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;

public abstract class AbstractTicketPricingStrategy implements TicketPricingStrategy {
    protected final TicketPricingProperties pricing;

    protected AbstractTicketPricingStrategy(TicketPricingProperties pricing) {
        this.pricing = pricing;
    }
}