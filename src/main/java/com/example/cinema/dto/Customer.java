package com.example.cinema.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record Customer(
        @NotBlank String name,
        @Min(0) int age
) {}