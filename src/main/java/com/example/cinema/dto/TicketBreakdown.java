package com.example.cinema.dto;

public record TicketBreakdown(TicketType ticketType, int quantity, double totalCost) {}