package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdultTicketPricingStrategyTest {

    @Test
    void calculateCost_returnsCorrectCost() {
        TicketPricingProperties props = new TicketPricingProperties();
        props.setAdult(12.5);

        AdultTicketPricingStrategy strategy = new AdultTicketPricingStrategy(props);

        assertEquals(25.0, strategy.calculateCost(2));
        assertEquals(0.0, strategy.calculateCost(0));
    }
}