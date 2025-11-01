package com.example.cinema.pricing;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TicketType {
    ADULT("Adult"),
    SENIOR("Senior"),
    TEEN("Teen"),
    CHILDREN("Children");

    private final String value;

    TicketType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static TicketType fromAge(int age) {
        if (age < 11) return CHILDREN;
        if (age < 18) return TEEN;
        if (age < 65) return ADULT;
        return SENIOR;
    }
}