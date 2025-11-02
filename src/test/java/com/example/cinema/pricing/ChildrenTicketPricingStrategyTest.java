package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChildrenTicketPricingStrategyTest {

    @Test
    void calculateCost_noDiscountBelowThreshold() {
        TicketPricingProperties props = new TicketPricingProperties();
        props.setChildren(8.0);
        props.setChildrenDiscount(0.25); // 25% discount
        props.setChildrenDiscountThreshold(3);

        ChildrenTicketPricingStrategy strategy = new ChildrenTicketPricingStrategy(props);

        // Below threshold, no discount
        assertEquals(16.0, strategy.calculateCost(2), 0.001);
    }

    @Test
    void calculateCost_discountAtThreshold() {
        TicketPricingProperties props = new TicketPricingProperties();
        props.setChildren(8.0);
        props.setChildrenDiscount(0.25); // 25% discount
        props.setChildrenDiscountThreshold(3);

        ChildrenTicketPricingStrategy strategy = new ChildrenTicketPricingStrategy(props);

        // At threshold, discount applies: 3 * 8 = 24, 25% off = 18
        assertEquals(18.0, strategy.calculateCost(3), 0.001);
    }

    @Test
    void calculateCost_discountAboveThreshold() {
        TicketPricingProperties props = new TicketPricingProperties();
        props.setChildren(8.0);
        props.setChildrenDiscount(0.25); // 25% discount
        props.setChildrenDiscountThreshold(3);

        ChildrenTicketPricingStrategy strategy = new ChildrenTicketPricingStrategy(props);

        // Above threshold, discount applies: 4 * 8 = 32, 25% off = 24
        assertEquals(24.0, strategy.calculateCost(4), 0.001);
    }

    @Test
    void calculateCost_zeroQuantity() {
        TicketPricingProperties props = new TicketPricingProperties();
        props.setChildren(8.0);
        props.setChildrenDiscount(0.25);
        props.setChildrenDiscountThreshold(3);

        ChildrenTicketPricingStrategy strategy = new ChildrenTicketPricingStrategy(props);

        assertEquals(0.0, strategy.calculateCost(0), 0.001);
    }
}