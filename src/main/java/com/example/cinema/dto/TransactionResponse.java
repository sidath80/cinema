package com.example.cinema.dto;

import java.util.List;

public record TransactionResponse(int transactionId, List<TicketBreakdown> tickets, double totalCost) {}