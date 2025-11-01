package com.example.cinema.controller;

import com.example.cinema.dto.TransactionRequest;
import com.example.cinema.dto.TransactionResponse;
import com.example.cinema.service.TicketCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@Validated
public class TicketController {
    private final Map<String, TicketCalculator> calculators;
    @Value("${ticket.calculator}")
    private String calculatorType;

    @PostMapping("/calculate")
    public TransactionResponse calculate(@Valid @RequestBody TransactionRequest request) {
        TicketCalculator ticketCalculator = calculators.get(calculatorType);
        if (ticketCalculator == null) {
            throw new IllegalArgumentException("Unknown calculator: " + calculatorType);
        }
        return ticketCalculator.calculate(request);
    }
}