package com.example.cinema.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ticket.pricing")
public class TicketPricingProperties {
    private double adult = 25.0;
    private double seniorDiscount = 0.3;
    private double teen = 12.0;
    private double children = 5.0;
    private int childrenDiscountThreshold = 3;
    private double childrenDiscount = 0.25;
}