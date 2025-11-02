package com.example.cinema.service;

import com.example.cinema.dto.Customer;
import com.example.cinema.dto.TransactionRequest;
import com.example.cinema.dto.TransactionResponse;
import com.example.cinema.pricing.TicketPricingStrategy;
import com.example.cinema.pricing.TicketType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service("defaultTicketCalculator")
@RequiredArgsConstructor
public class TicketCalculatorService implements TicketCalculator {
    private final TransactionResponseService transactionResponseService;
    private final List<TicketPricingStrategy> ticketPricingStrategyList;

    @Override
    public TransactionResponse calculate(TransactionRequest request) {
        Map<TicketType, Integer> typeCount = new EnumMap<>(TicketType.class);
        for (Customer customer : request.customers()) {
            TicketType ticketType = TicketType.fromAge(customer.age());
            typeCount.merge(ticketType, 1, Integer::sum);
        }

        Map<TicketType, TicketPricingStrategy> strategyMap = new EnumMap<>(TicketType.class);
        for (TicketPricingStrategy strategy : ticketPricingStrategyList) {
            strategyMap.put(strategy.getSupportedType(), strategy);
        }

        return transactionResponseService.create(
                request.transactionId(),
                typeCount,
                strategyMap
        );
    }
}