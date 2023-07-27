package com.oskar;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

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

    @Disabled("Still needs to be finished")
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

    @DisplayName("Division by 0")
    @Test
    void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmeticException(){
        int dividend = 4;
        int divisor = 0;
        String expectedExceptionMessage = "/ by zero";

        ArithmeticException e = assertThrows(ArithmeticException.class,
                ()-> {
                    calculator.division(dividend, divisor);
                }, "Division by zero should have thrown an Arithmetic exception");

        assertEquals(expectedExceptionMessage, e.getMessage(), "Unexpected exception message");
    }

    @DisplayName("Parametrized subtraction test")
    @ParameterizedTest
    @MethodSource("subtractionInputParameters") //<- name of method which provides input parameters
//    @CsvSource( { <- we dont neeed above line and the function
//                  "33, 1, 32",
//                  "42, 5, 37"
//                 } )
//    @CsvSource({
//            "apple, orange",
//            "apple, '' <- empty",
//            "apple, <- null"
//    })
//    @CsvFileSource(resources = "/integerDivison.csv")
//    we have to create package in test directory and mark as test resources
//    in csv files parameters should be like 44,4,40 newline 50,1,49
    void subtractionParametrized(int minuend, int subtrahend, int expectedResult){
        int result = calculator.subtraction(minuend, subtrahend);

        //Assert //Then
        assertEquals(expectedResult, result, ()->minuend + "-" + subtrahend + "did not produce " + expectedResult
                + ", " + result + " was produced"); //<- it is good to put a message as a lambda, as it is computed only

    }


    private static Stream<Arguments> subtractionInputParameters(){
        return Stream.of(
                Arguments.of(33, 1, 32),
                Arguments.of(54, 1, 53),
                Arguments.of(24, 25, -1)
        );
    }


    @ParameterizedTest
    @ValueSource(strings = {"John", "Oskar", "Kuba"})
    void valueSourceDemonstration(String firstName){
        System.out.println(firstName);
        assertNotNull(firstName);
    }
}