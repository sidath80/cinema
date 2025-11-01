package com.example.cinema.pricing;

import com.example.cinema.config.TicketPricingProperties;
import org.springframework.stereotype.Component;

@Component
public class ChildrenTicketPricingStrategy implements TicketPricingStrategy {
    @Override
    public double calculateCost(int qty, TicketPricingProperties pricing) {
        double price = pricing.getChildren() * qty;
        if (qty >= pricing.getChildrenDiscountThreshold()) {
            price *= (1 - pricing.getChildrenDiscount());
        }
        return price;
    }

    @Override
    public TicketType getSupportedType() {
        return TicketType.CHILDREN;
    }
}