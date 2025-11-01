package com.example.cinema.service;

import com.example.cinema.dto.TransactionRequest;
import com.example.cinema.dto.TransactionResponse;

public interface TicketCalculator {
    TransactionResponse calculate(TransactionRequest request);
}
