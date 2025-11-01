package com.example.cinema.service;

import com.example.cinema.dto.Customer;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        Customer customer = new Customer("   ", 10);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void shouldFailWhenAgeIsNegative() {
        Customer customer = new Customer("John", -1);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("age"));
    }

    @Test
    void shouldPassWhenValid() {
        Customer customer = new Customer("Alice", 5);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).isEmpty();
    }
}