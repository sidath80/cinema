package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeniorTicketPricingStrategyTest {

    @Test
    void calculateCost_noDiscount() {
        TicketPricingProperties props = new TicketPricingProperties();
        props.setAdult(10.0);
        props.setSeniorDiscount(0.0);

        SeniorTicketPricingStrategy strategy = new SeniorTicketPricingStrategy(props);

        assertEquals(30.0, strategy.calculateCost(3), 0.001);
        assertEquals(0.0, strategy.calculateCost(0), 0.001);
    }

    @Test
    void calculateCost_withDiscount() {
        TicketPricingProperties props = new TicketPricingProperties();
        props.setAdult(10.0);
        props.setSeniorDiscount(0.2); // 20% discount

        SeniorTicketPricingStrategy strategy = new SeniorTicketPricingStrategy(props);

        // 3 * 10 * (1 - 0.2) = 24.0
        assertEquals(24.0, strategy.calculateCost(3), 0.001);
    }

    @Test
    void calculateCost_fullDiscount() {
        TicketPricingProperties props = new TicketPricingProperties();
        props.setAdult(10.0);
        props.setSeniorDiscount(1.0); // 100% discount

        SeniorTicketPricingStrategy strategy = new SeniorTicketPricingStrategy(props);

        assertEquals(0.0, strategy.calculateCost(3), 0.001);
    }
}