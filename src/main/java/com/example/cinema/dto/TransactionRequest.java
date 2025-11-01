package com.example.cinema.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record TransactionRequest(
        @Min(1) int transactionId,
        @NotNull @NotEmpty List<@Valid @NotNull Customer> customers
) {}