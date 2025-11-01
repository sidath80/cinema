package com.example.cinema.config;

import com.example.cinema.service.TicketCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TicketCalculatorConfig {
    @Bean
    @Primary
    public TicketCalculator ticketCalculator(
            ApplicationContext ctx,
            @Value("${ticket.calculator}") String calculatorBeanName) {
        return (TicketCalculator) ctx.getBean(calculatorBeanName);
    }
}