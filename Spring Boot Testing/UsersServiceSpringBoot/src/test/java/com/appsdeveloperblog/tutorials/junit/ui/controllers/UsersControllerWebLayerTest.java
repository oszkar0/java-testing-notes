package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.service.UsersService;
import com.appsdeveloperblog.tutorials.junit.service.UsersServiceImpl;
import com.appsdeveloperblog.tutorials.junit.shared.UserDto;
import com.appsdeveloperblog.tutorials.junit.ui.request.UserDetailsRequestModel;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UsersController.class,
excludeAutoConfiguration = {SecurityAutoConfiguration.class}) //create only controller beans
// this or one above (excludeAutoConfiguration...) @AutoConfigureMockMvc(addFilters = false) //disable spring security filters
@MockBean({UsersServiceImpl.class})
public class UsersControllerWebLayerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired //autowired and @MockBean above class or instead of @Autowired we put @MockBean above field
    UsersService usersService;

    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidDetailsProvided_returnsCreatedUserDetails() throws Exception {
        //Arrange
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("Oskar");
        userDetailsRequestModel.setLastName("Szysiak");
        userDetailsRequestModel.setEmail("email@email.com");
        userDetailsRequestModel.setPassword("123456789");
        userDetailsRequestModel.setRepeatPassword("123456789");

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder =  MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserRest createdUser = new ObjectMapper().readValue(responseBodyAsString, UserRest.class);

        //Assert
        Assertions.assertEquals(userDetailsRequestModel.getFirstName(),
                createdUser.getFirstName(), "The returned users first name is most likely incorrect");

        Assertions.assertEquals(userDetailsRequestModel.getEmail(),
                createdUser.getEmail(), "The returned users email is most likely incorrect");

        Assertions.assertEquals(userDetailsRequestModel.getLastName(),
                createdUser.getLastName(), "The returned users last name is most likely incorrect");

        Assertions.assertFalse(createdUser.getUserId().isEmpty(), "User id should not be empty");
    }
}
