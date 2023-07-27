package com.oskar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    //naming convention for tests
    //test<System Under Test>_<Condition or State Change>_<Expected result>
    @org.junit.jupiter.api.Test
    void testDivision_WhenFourDividedByTwo_ShouldReturnTwo() {
        Calculator calculator = new Calculator();
        int result = calculator.division(4, 2);

        assertEquals(2 , result, "Test failed, 4/2 did not produce 2"); //<- message is optional, will be printed if test is failed
    }

    @Test
    void testSubtraction_WhenNineIsSubtractedFromSeven_ShouldReturnMinusTwo() {
        Calculator calculator = new Calculator();

        int minuend = 7;
        int subtrahend = 9;
        int expectedResult = -2;

        int result = calculator.subtraction(minuend, subtrahend);

        assertEquals(expectedResult, result, ()->minuend + "-" + subtrahend + "did not produce " + expectedResult
                + ", " + result + " was produced"); //<- it is good to put a message as a lambda, as it is computed only
                                                    //in case when test fails, if it is normal message it is always computed
    }
}