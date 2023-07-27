package com.oskar;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @org.junit.jupiter.api.Test
    void division() {
        Calculator calculator = new Calculator();
        int result = calculator.division(4, 2);

        assertEquals(2 , result, "Test failed, 4/2 did not produce 2"); //<- message is optional, will be printed if test is failed
    }
}