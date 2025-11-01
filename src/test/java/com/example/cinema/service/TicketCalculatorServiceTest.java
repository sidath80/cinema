package com.example.cinema.service;

import com.example.cinema.config.TicketPricingProperties;
import com.example.cinema.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketCalculatorServiceTest {

    private TicketPricingProperties pricing;
    private TicketCalculatorService service;

    @BeforeEach
    void setUp() {
        pricing = mock(TicketPricingProperties.class);
        when(pricing.getAdult()).thenReturn(10.0);
        when(pricing.getTeen()).thenReturn(7.0);
        when(pricing.getChildren()).thenReturn(5.0);
        when(pricing.getSeniorDiscount()).thenReturn(0.2); // 20%
        when(pricing.getChildrenDiscount()).thenReturn(0.1); // 10%
        when(pricing.getChildrenDiscountThreshold()).thenReturn(3);
        service = new TicketCalculatorService(pricing);
    }

    @Test
    void calculate_singleAdult() {
        TransactionRequest req = new TransactionRequest(1, List.of(new Customer("A", 30)));
        TransactionResponse resp = service.calculate(req);

        assertEquals(1, resp.tickets().size());
        TicketBreakdown tb = resp.tickets().get(0);
        assertEquals(TicketType.ADULT, tb.ticketType());
        assertEquals(1, tb.quantity());
        assertEquals(10.0, tb.totalCost());
        assertEquals(10.0, resp.totalCost());
    }

    @Test
    void calculate_singleSenior() {
        TransactionRequest req = new TransactionRequest(1, List.of(new Customer("S", 70)));
        TransactionResponse resp = service.calculate(req);

        assertEquals(1, resp.tickets().size());
        TicketBreakdown tb = resp.tickets().get(0);
        assertEquals(TicketType.SENIOR, tb.ticketType());
        assertEquals(1, tb.quantity());
        assertEquals(8.0, tb.totalCost()); // 10 - 20%
        assertEquals(8.0, resp.totalCost());
    }

    @Test
    void calculate_singleTeen() {
        TransactionRequest req = new TransactionRequest(1, List.of(new Customer("T", 15)));
        TransactionResponse resp = service.calculate(req);

        assertEquals(1, resp.tickets().size());
        TicketBreakdown tb = resp.tickets().get(0);
        assertEquals(TicketType.TEEN, tb.ticketType());
        assertEquals(1, tb.quantity());
        assertEquals(7.0, tb.totalCost());
        assertEquals(7.0, resp.totalCost());
    }

    @Test
    void calculate_singleChildren_noDiscount() {
        TransactionRequest req = new TransactionRequest(1, List.of(new Customer("C", 5)));
        TransactionResponse resp = service.calculate(req);

        assertEquals(1, resp.tickets().size());
        TicketBreakdown tb = resp.tickets().get(0);
        assertEquals(TicketType.CHILDREN, tb.ticketType());
        assertEquals(1, tb.quantity());
        assertEquals(5.0, tb.totalCost());
        assertEquals(5.0, resp.totalCost());
    }

    @Test
    void calculate_multipleChildren_withDiscount() {
        TransactionRequest req = new TransactionRequest(4, List.of(
                new Customer("C1", 5),
                new Customer("C2", 6),
                new Customer("C3", 7),
                new Customer("C4", 8)
        ));
        TransactionResponse resp = service.calculate(req);

        assertEquals(1, resp.tickets().size());
        TicketBreakdown tb = resp.tickets().get(0);
        assertEquals(TicketType.CHILDREN, tb.ticketType());
        assertEquals(4, tb.quantity());
        assertEquals(18.0, tb.totalCost()); // 4*5=20, 10% off = 18
        assertEquals(18.0, resp.totalCost());
    }

    @Test
    void calculate_mixedTypes() {
        TransactionRequest req = new TransactionRequest(4, List.of(
                new Customer("A", 30), // ADULT
                new Customer("S", 70), // SENIOR
                new Customer("T", 15), // TEEN
                new Customer("C", 5)   // CHILDREN
        ));
        TransactionResponse resp = service.calculate(req);

        assertEquals(4, resp.tickets().size());
        assertEquals(10.0 + 8.0 + 7.0 + 5.0, resp.totalCost());
    }

    @Test
    void calculate_emptyCustomers() {
        TransactionRequest req = new TransactionRequest(0, List.of());
        TransactionResponse resp = service.calculate(req);

        assertEquals(0, resp.tickets().size());
        assertEquals(0.0, resp.totalCost());
    }
}