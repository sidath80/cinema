package com.example.cinema.dto;

import com.example.cinema.pricing.TicketType;

public record TicketBreakdown(TicketType ticketType, int quantity, double totalCost) {}