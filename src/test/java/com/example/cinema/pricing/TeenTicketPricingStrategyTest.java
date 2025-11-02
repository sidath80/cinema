package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeenTicketPricingStrategyTest {

    @Test
    void calculateCost_returnsCorrectCost() {
        TicketPricingProperties props = new TicketPricingProperties();
        props.setTeen(10.0);

        TeenTicketPricingStrategy strategy = new TeenTicketPricingStrategy(props);

        assertEquals(30.0, strategy.calculateCost(3));
        assertEquals(0.0, strategy.calculateCost(0));
    }
}