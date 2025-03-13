package com.jcgomis.kata_foo_bar_quix;

import com.jcgomis.kata_foo_bar_quix.services.impl.ConvertServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConvertServiceImplTest {
    private ConvertServiceImpl convertService;

    @BeforeEach
    void setUp() {
        convertService = new ConvertServiceImpl();
    }

    @Test
    void shouldReturnFOOWhenNumberIsDivisibleBy3() {
        // Given
        int number = 6; // divisible par 3 mais ne contient pas 3, 5 ou 7

        // When
        String result = convertService.convertNumber(number);

        // Then
        assertEquals("FOO", result);
    }

    @Test
    void shouldReturnBARWhenNumberIsDivisibleBy5() {
        // Given
        int number = 10; // divisible par 5 mais ne contient pas 3, 5 ou 7

        // When
        String result = convertService.convertNumber(number);

        // Then
        assertEquals("BAR", result);
    }

    @Test
    void shouldReturnFOOBARWhenNumberIsDivisibleBy3And5() {
        // Given
        int number = 15; // divisible par 3 et 5, contient 5

        // When
        String result = convertService.convertNumber(number);

        // Then
        assertEquals("FOOBARBAR", result);
    }

    @Test
    void shouldReturnFOOWhenNumberContains3() {
        // Given
        int number = 13; // contient 3, non divisible par 3 ou 5

        // When
        String result = convertService.convertNumber(number);

        // Then
        assertEquals("FOO", result);
    }

    @Test
    void shouldReturnBARWhenNumberContains5() {
        // Given
        int number = 52; // contient 5, non divisible par 3 ou 5

        // When
        String result = convertService.convertNumber(number);

        // Then
        assertEquals("BAR", result);
    }

    @Test
    void shouldReturnQUIXWhenNumberContains7() {
        // Given
        int number = 17; // contient 7, non divisible par 3 ou 5

        // When
        String result = convertService.convertNumber(number);

        // Then
        assertEquals("QUIX", result);
    }

    @Test
    void shouldReturnNumberAsStringWhenNoRuleApplies() {
        // Given
        int number = 1; // non divisible par 3 ou 5, ne contient pas 3, 5 ou 7

        // When
        String result = convertService.convertNumber(number);

        // Then
        assertEquals("1", result);
    }

    @Test
    void shouldHandleMultipleDigits() {
        // Given
        int number = 73; // contient 7 et 3

        // When
        String result = convertService.convertNumber(number);

        // Then
        assertEquals("QUIXFOO", result);
    }

    @Test
    void shouldThrowExceptionWhenNumberIsLessThan0() {
        // Given
        int number = -1;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> convertService.convertNumber(number));
    }

    @Test
    void shouldThrowExceptionWhenNumberIsGreaterThan100() {
        // Given
        int number = 101;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> convertService.convertNumber(number));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 2",
            "3, FOOFOO",
            "5, BARBAR",
            "7, QUIX",
            "9, FOO",
            "10, BAR",
            "15, FOOBARBAR",
            "21, FOO",
            "33, FOOFOOFOO",
            "51, FOOBAR",
            "53, BARFOO",
            "55, BARBARBAR",
            "70, BARQUIX",
            "73, QUIXFOO",
            "75, FOOBARQUIXBAR",
            "77, QUIXQUIX",
            "99, FOO"
    })
    void shouldHandleVariousInputs(int input, String expected) {
        assertEquals(expected, convertService.convertNumber(input));
    }
}
