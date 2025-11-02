package com.example.cinema.service;

import com.example.cinema.dto.Customer;
import com.example.cinema.dto.TransactionRequest;
import com.example.cinema.dto.TransactionResponse;
import com.example.cinema.pricing.TicketPricingStrategy;
import com.example.cinema.pricing.TicketType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class TicketCalculatorServiceTest {

    private TicketPricingStrategy adultStrategy;
    private TicketPricingStrategy childStrategy;
    private TicketPricingStrategy seniorStrategy;
    private TicketPricingStrategy teenStrategy;

    private TicketCalculatorService service;
    private TransactionResponseService transactionResponseService;

    @BeforeEach
    void setUp() {
        transactionResponseService = mock(TransactionResponseService.class);

        childStrategy = mock(TicketPricingStrategy.class);
        when(childStrategy.getSupportedType()).thenReturn(TicketType.CHILDREN);

        adultStrategy = mock(TicketPricingStrategy.class);
        when(adultStrategy.getSupportedType()).thenReturn(TicketType.ADULT);

        seniorStrategy = mock(TicketPricingStrategy.class);
        when(seniorStrategy.getSupportedType()).thenReturn(TicketType.SENIOR);

        teenStrategy = mock(TicketPricingStrategy.class);
        when(teenStrategy.getSupportedType()).thenReturn(TicketType.TEEN);

        service = new TicketCalculatorService(
                transactionResponseService,
                List.of(childStrategy, adultStrategy, seniorStrategy, teenStrategy)
        );
    }

    @Test
    void calculate_successful() {
        Customer c1 = new Customer("Alice", 5);    // CHILDREN
        Customer c2 = new Customer("Bob", 30);     // ADULT
        Customer c3 = new Customer("Eve", 70);     // SENIOR
        Customer c4 = new Customer("Tom", 16);     // Teen

        TransactionRequest request = new TransactionRequest(1, List.of(c1, c2, c3, c4));
        TransactionResponse expected = mock(TransactionResponse.class);

        when(transactionResponseService.create(anyInt(), any(), any())).thenReturn(expected);

        TransactionResponse result = service.calculate(request);

        assertThat(result).isSameAs(expected);

        ArgumentCaptor<Map<TicketType, Integer>> typeCountCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<TicketType, TicketPricingStrategy>> strategyMapCaptor = ArgumentCaptor.forClass(Map.class);

        verify(transactionResponseService).create(eq(1), typeCountCaptor.capture(), strategyMapCaptor.capture());

        Map<TicketType, Integer> typeCount = typeCountCaptor.getValue();
        assertThat(typeCount.get(TicketType.CHILDREN)).isEqualTo(1);
        assertThat(typeCount.get(TicketType.ADULT)).isEqualTo(1);
        assertThat(typeCount.get(TicketType.SENIOR)).isEqualTo(1);
        assertThat(typeCount.get(TicketType.TEEN)).isEqualTo(1);

        Map<TicketType, TicketPricingStrategy> strategyMap = strategyMapCaptor.getValue();
        assertThat(strategyMap.get(TicketType.CHILDREN)).isSameAs(childStrategy);
        assertThat(strategyMap.get(TicketType.ADULT)).isSameAs(adultStrategy);
        assertThat(strategyMap.get(TicketType.SENIOR)).isSameAs(seniorStrategy);
        assertThat(strategyMap.get(TicketType.TEEN)).isSameAs(teenStrategy);
    }

    @Test
    void calculate_shouldThrow_whenResponseServiceFails() {
        Customer c1 = new Customer("Alice", 5);
        TransactionRequest request = new TransactionRequest(1, List.of(c1));

        when(transactionResponseService.create(anyInt(), any(), any()))
                .thenThrow(new RuntimeException("Service error"));

        assertThatThrownBy(() -> service.calculate(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Service error");
    }
}