package com.oskar;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Order(3)
@DisplayName("Test math operations in Calculator class, repeated test")
class DemoRepeatedTest {
    Calculator calculator;

    @BeforeEach
    void setup(){
        this.calculator = new Calculator();
    }
    @DisplayName("Division by 0")
    @RepeatedTest(value = 3, name = "{displayName}. Repetition {currentRepetition}") // <- repeated 3 times
    void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmeticException(RepetitionInfo repetitionInfo){
        System.out.println("Repetition number: " + repetitionInfo.getCurrentRepetition());
        int dividend = 4;
        int divisor = 0;
        String expectedExceptionMessage = "/ by zero";

        ArithmeticException e = assertThrows(ArithmeticException.class,
                ()-> {
                    calculator.division(dividend, divisor);
                }, "Division by zero should have thrown an Arithmetic exception");

        assertEquals(expectedExceptionMessage, e.getMessage(), "Unexpected exception message");
    }
}