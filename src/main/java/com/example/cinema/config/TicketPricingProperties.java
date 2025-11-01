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
    private double adult;
    private double seniorDiscount;
    private double teen;
    private double children;
    private int childrenDiscountThreshold;
    private double childrenDiscount;
}