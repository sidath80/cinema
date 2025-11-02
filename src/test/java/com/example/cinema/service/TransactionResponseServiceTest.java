package com.example.cinema.service;

import com.example.cinema.dto.TicketBreakdown;
import com.example.cinema.dto.TransactionResponse;
import com.example.cinema.pricing.TicketPricingStrategy;
import com.example.cinema.pricing.TicketType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionResponseServiceTest {

    private final TransactionResponseService service = new TransactionResponseService();

    @Test
    void create_shouldReturnCorrectBreakdownAndTotal() {
        TicketPricingStrategy childStrategy = mock(TicketPricingStrategy.class);
        TicketPricingStrategy adultStrategy = mock(TicketPricingStrategy.class);

        when(childStrategy.calculateCost(2)).thenReturn(10.0);
        when(adultStrategy.calculateCost(1)).thenReturn(15.0);

        Map<TicketType, Integer> typeCount = Map.of(
                TicketType.CHILDREN, 2,
                TicketType.ADULT, 1
        );
        Map<TicketType, TicketPricingStrategy> strategyMap = Map.of(
                TicketType.CHILDREN, childStrategy,
                TicketType.ADULT, adultStrategy
        );

        TransactionResponse response = service.create(42, typeCount, strategyMap);

        assertThat(response.transactionId()).isEqualTo(42);
        assertThat(response.totalCost()).isEqualTo(25.0);

        List<TicketBreakdown> breakdowns = response.tickets();
        assertThat(breakdowns).hasSize(2);
        assertThat(breakdowns)
                .anySatisfy(tb -> {
                    assertThat(tb.ticketType()).isEqualTo(TicketType.CHILDREN);
                    assertThat(tb.quantity()).isEqualTo(2);
                    assertThat(tb.totalCost()).isEqualTo(10.0);
                })
                .anySatisfy(tb -> {
                    assertThat(tb.ticketType()).isEqualTo(TicketType.ADULT);
                    assertThat(tb.quantity()).isEqualTo(1);
                    assertThat(tb.totalCost()).isEqualTo(15.0);
                });
    }

    @Test
    void create_shouldSortBreakdownsByTicketTypeName() {
        TicketPricingStrategy a = mock(TicketPricingStrategy.class);
        TicketPricingStrategy b = mock(TicketPricingStrategy.class);

        when(a.calculateCost(1)).thenReturn(5.0);
        when(b.calculateCost(1)).thenReturn(7.0);

        Map<TicketType, Integer> typeCount = Map.of(
                TicketType.SENIOR, 1,
                TicketType.ADULT, 1
        );
        Map<TicketType, TicketPricingStrategy> strategyMap = Map.of(
                TicketType.SENIOR, a,
                TicketType.ADULT, b
        );

        TransactionResponse response = service.create(1, typeCount, strategyMap);

        List<TicketBreakdown> breakdowns = response.tickets();
        assertThat(breakdowns).hasSize(2);
        assertThat(breakdowns.get(0).ticketType().name())
                .isLessThan(breakdowns.get(1).ticketType().name());
    }

    @Test
    void create_shouldHandleEmptyTypeCount() {
        TransactionResponse response = service.create(99, Map.of(), Map.of());
        assertThat(response.transactionId()).isEqualTo(99);
        assertThat(response.totalCost()).isEqualTo(0.0);
        assertThat(response.tickets()).isEmpty();
    }
}
