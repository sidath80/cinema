package com.example.cinema.service;

import com.example.cinema.config.TicketPricingProperties;
import com.example.cinema.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("defaultTicketCalculator")
@RequiredArgsConstructor
public class TicketCalculatorService implements TicketCalculator{
    private final TicketPricingProperties pricing;

    public TransactionResponse calculate(TransactionRequest request) {
        Map<TicketType, Integer> typeCount = new EnumMap<>(TicketType.class);
        for (Customer customer : request.customers()) {
            TicketType ticketType = TicketType.fromAge(customer.age());
            typeCount.merge(ticketType, 1, Integer::sum);
        }

        List<TicketBreakdown> breakdowns = new ArrayList<>();
        double total = 0.0;

        for (var entry : typeCount.entrySet()) {
            int qty = entry.getValue();
            double cost = getCost(entry.getKey(), qty);
            breakdowns.add(new TicketBreakdown(entry.getKey(), qty, cost));
            total += cost;
        }

        breakdowns.sort(Comparator.comparing(tb -> tb.ticketType().name()));
        return new TransactionResponse(request.transactionId(), breakdowns, total);
    }

    private double getCost(TicketType type, int qty) {
        return switch (type) {
            case ADULT -> qty * pricing.getAdult();
            case SENIOR -> qty * pricing.getAdult() * (1 - pricing.getSeniorDiscount());
            case TEEN -> qty * pricing.getTeen();
            case CHILDREN -> {
                double price = pricing.getChildren() * qty;
                if (qty >= pricing.getChildrenDiscountThreshold()) {
                    price *= (1 - pricing.getChildrenDiscount());
                }
                yield price;
            }
        };
    }
}