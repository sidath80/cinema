package com.example.cinema.service;

import com.example.cinema.config.TicketPricingProperties;
import com.example.cinema.dto.TicketBreakdown;
import com.example.cinema.dto.TransactionResponse;
import com.example.cinema.pricing.TicketPricingStrategy;
import com.example.cinema.pricing.TicketType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class TransactionResponseService {
    public TransactionResponse create(
            int transactionId,
            Map<TicketType, Integer> typeCount,
            Map<TicketType, TicketPricingStrategy> strategyMap,
            TicketPricingProperties pricing
    ) {
        List<TicketBreakdown> breakdowns = new ArrayList<>();
        double total = 0.0;

        for (var entry : typeCount.entrySet()) {
            TicketPricingStrategy strategy = strategyMap.get(entry.getKey());
            int qty = entry.getValue();
            double cost = strategy.calculateCost(qty, pricing);
            breakdowns.add(new TicketBreakdown(entry.getKey(), qty, cost));
            total += cost;
        }
        breakdowns.sort(Comparator.comparing(tb -> tb.ticketType().name()));
        return new TransactionResponse(transactionId, breakdowns, total);
    }
}