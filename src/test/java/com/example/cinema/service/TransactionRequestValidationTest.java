package com.example.cinema.service;

import com.example.cinema.dto.Customer;
import com.example.cinema.dto.TransactionRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenTransactionIdIsLessThanOne() {
        TransactionRequest req = new TransactionRequest(0, List.of(new Customer("John", 10)));
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("transactionId"));
    }

    @Test
    void shouldFailWhenCustomersIsNull() {
        TransactionRequest req = new TransactionRequest(1, null);
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("customers"));
    }

    @Test
    void shouldFailWhenCustomersIsEmpty() {
        TransactionRequest req = new TransactionRequest(1, List.of());
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("customers"));
    }

    @Test
    void shouldPassWhenValid() {
        TransactionRequest req = new TransactionRequest(1, List.of(new Customer("Alice", 5)));
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }
}
