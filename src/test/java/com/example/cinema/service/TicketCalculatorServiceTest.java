package com.example.cinema.service;

import com.example.cinema.config.TicketPricingProperties;
import com.example.cinema.dto.*;
import com.example.cinema.pricing.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class TicketCalculatorServiceTest {

    private TicketCalculatorService service;
    private TicketPricingProperties pricing;

    @BeforeEach
    void setUp() {
        pricing = new TicketPricingProperties();
        pricing.setAdult(10.0);
        pricing.setTeen(7.0);
        pricing.setChildren(5.0);
        pricing.setSeniorDiscount(0.2); // 20%
        pricing.setChildrenDiscount(0.1); // 10%
        pricing.setChildrenDiscountThreshold(3);

        service = new TicketCalculatorService(
                pricing,
                List.of(
                        new AdultTicketPricingStrategy(),
                        new SeniorTicketPricingStrategy(),
                        new TeenTicketPricingStrategy(),
                        new ChildrenTicketPricingStrategy()
                ),
                new TransactionResponseService()
        );
    }

    @Test
    void calculate_singleAdult() {
        TransactionRequest req = new TransactionRequest(1, List.of(new Customer("A", 30)));
        TransactionResponse resp = service.calculate(req);

        assertEquals(1, resp.tickets().size());
        assertEquals(TicketType.ADULT, resp.tickets().get(0).ticketType());
        assertEquals(10.0, resp.totalCost());
    }

    @Test
    void calculate_singleSenior() {
        TransactionRequest req = new TransactionRequest(1, List.of(new Customer("S", 70)));
        TransactionResponse resp = service.calculate(req);

        assertEquals(1, resp.tickets().size());
        assertEquals(TicketType.SENIOR, resp.tickets().get(0).ticketType());
        assertEquals(8.0, resp.totalCost()); // 10 - 20%
    }

    @Test
    void calculate_singleTeen() {
        TransactionRequest req = new TransactionRequest(1, List.of(new Customer("T", 15)));
        TransactionResponse resp = service.calculate(req);

        assertEquals(1, resp.tickets().size());
        assertEquals(TicketType.TEEN, resp.tickets().get(0).ticketType());
        assertEquals(7.0, resp.totalCost());
    }

    @Test
    void calculate_singleChildren_noDiscount() {
        TransactionRequest req = new TransactionRequest(1, List.of(new Customer("C", 5)));
        TransactionResponse resp = service.calculate(req);

        assertEquals(1, resp.tickets().size());
        assertEquals(TicketType.CHILDREN, resp.tickets().get(0).ticketType());
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
        assertEquals(TicketType.CHILDREN, resp.tickets().get(0).ticketType());
        assertEquals(18.0, resp.totalCost()); // 4*5=20, 10% off = 18
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