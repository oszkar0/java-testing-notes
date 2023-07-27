package com.oskar;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

//@TestMethodOrder(MethodOrderer.Random.class) <- every run method execution order is random
//@TestMethodOrder(MethodOrderer.MethodName.class) <- every run is the same
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodOrderTest {
    @Order(1)
    @Test
    void testA(){
        System.out.println("Running test A");
    }

    @Order(3)
    @Test
    void testB(){
        System.out.println("Running test B");
    }

    @Order((4))
    @Test
    void testC(){
        System.out.println("Running test C");
    }

    @Order(2)
    @Test
    void testD(){
        System.out.println("Running test D");
    }
}
