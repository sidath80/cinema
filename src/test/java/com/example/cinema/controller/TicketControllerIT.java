package com.example.cinema.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerIT {

    @Autowired
    private MockMvc mockMvc;

    static Stream<TestCase> successRequestResponseProvider() throws Exception {
        return Stream.of(
                new TestCase("testData/success/success-request1.json", "testData/success/success-response1.json"),
                new TestCase("testData/success/success-request2.json", "testData/success/success-response2.json"),
                new TestCase("testData/success/success-request3.json", "testData/success/success-response3.json")
                );
    }

    @ParameterizedTest
    @MethodSource("successRequestResponseProvider")
    void calculate_ReturnsExpectedResponse(TestCase testCase) throws Exception {
        String requestJson = Files.readString(Path.of("src/test/resources/" + testCase.requestFile));
        String expectedResponseJson = Files.readString(Path.of("src/test/resources/" + testCase.responseFile));

        mockMvc.perform(post("/api/tickets/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    static Stream<TestCase> errorRequestResponseProvider() throws Exception {
        return Stream.of(
                new TestCase("testData/error/error-request1.json", "testData/error/error-response1.json"),
                new TestCase("testData/error/error-request2.json", "testData/error/error-response2.json"),
                new TestCase("testData/error/error-request3.json", "testData/error/error-response3.json")
        );
    }

    @ParameterizedTest
    @MethodSource("errorRequestResponseProvider")
    void calculate_ErrorsExpectedResponse(TestCase testCase) throws Exception {
        String requestJson = Files.readString(Path.of("src/test/resources/" + testCase.requestFile));
        String expectedResponseJson = Files.readString(Path.of("src/test/resources/" + testCase.responseFile));

        mockMvc.perform(post("/api/tickets/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(expectedResponseJson));
    }

    static class TestCase {
        final String requestFile;
        final String responseFile;

        TestCase(String requestFile, String responseFile) {
            this.requestFile = requestFile;
            this.responseFile = responseFile;
        }
    }
}