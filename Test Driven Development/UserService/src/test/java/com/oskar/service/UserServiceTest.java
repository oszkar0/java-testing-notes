package com.oskar.service;

import com.oskar.model.User;
import com.oskar.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//TDD - We start with writing the test, everytime test doesnt compile, we have to stop
//writing test and make some changes in things we are testing
public class UserServiceTest {
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        //Arrange
        UserService userService = new UserServiceImpl();
        String firstName = "Oskar";
        String lastName = "Szysiak";
        String email = "test@test.com";
        String password = "12345678";
        String repeatedPassword = "12345678";

        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatedPassword);

        //Assert
        assertNotNull(user, "The createUser() should not have returned null");
    }
}
