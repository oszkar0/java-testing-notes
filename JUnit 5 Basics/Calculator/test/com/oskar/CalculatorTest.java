package com.oskar;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test math operations in Calculator class")
class CalculatorTest {
    Calculator calculator;
    @BeforeAll
    static void setup(){
        System.out.println("Executing @BeforeAll method");
    }

    @AfterAll
    static  void cleanup(){
        System.out.println("Executing @AfterAll method");
    }

    @BeforeEach
    void beforeEachTest(){
        this.calculator = new Calculator();
        System.out.println("Executing @BeforeEach method");
    }



    //naming convention for tests
    //test<System Under Test>_<Condition or State Change>_<Expected result>
    @DisplayName("Test 4/2=2")
    @org.junit.jupiter.api.Test
    void testDivision_WhenFourDividedByTwo_ShouldReturnTwo() {
        //Arrange
        int dividend = 4;
        int divisor = 2;
        int expectedResult = 2;

        //Act
        int result = calculator.division(4, 2);

        //Assert
        assertEquals(2 , result, "Test failed, 4/2 did not produce 2"); //<- message is optional, will be printed if test is failed
    }

    @DisplayName("Test 7-9=-2")
    @Test
    void testSubtraction_WhenNineIsSubtractedFromSeven_ShouldReturnMinusTwo() {
        //Arrange //Given
        int minuend = 7;
        int subtrahend = 9;
        int expectedResult = -2;

        //Act    //When
        int result = calculator.subtraction(minuend, subtrahend);

        //Assert //Then
        assertEquals(expectedResult, result, ()->minuend + "-" + subtrahend + "did not produce " + expectedResult
                + ", " + result + " was produced"); //<- it is good to put a message as a lambda, as it is computed only
                                                    //in case when test fails, if it is normal message it is always computed
    }
}