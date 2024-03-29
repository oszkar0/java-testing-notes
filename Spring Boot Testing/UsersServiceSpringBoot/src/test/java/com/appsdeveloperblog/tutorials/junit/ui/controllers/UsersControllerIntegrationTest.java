package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.security.SecurityConstants;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.List;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) Spring framework creates app context with mocked web environment
//Only Web Layer beans will be created

//Below annotation creates all layers on defined port in application.properties or the one we defined here
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //tells spring that we will be testing all layers
properties = "server.port=8081")
//or with @TestPropertySource(locations = "/app-test.properties") we can define another source
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate; //client which will allow us to send http request

    private String authorizationToken;
    @Test
    @DisplayName("User can be created")
    @Order(1)
    void testCreateUser_whenValidDetailsProvided_returnUserDetails() throws JSONException {
        //Arrange
        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("firstName", "Oskar");
        userDetailsRequestJson.put("lastName", "Szysiak");
        userDetailsRequestJson.put("email", "oskszy@kkk.com");
        userDetailsRequestJson.put("password", "123456789");
        userDetailsRequestJson.put("repeatPassword", "123456789");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJson.toString(), headers);

        //Act
        ResponseEntity<UserRest>  createdUserDetailsEntity = testRestTemplate.postForEntity("/users",
                request, UserRest.class);

        UserRest createdUserDetails = createdUserDetailsEntity.getBody();


        //Assert
        Assertions.assertEquals(HttpStatus.OK, createdUserDetailsEntity.getStatusCode());
        Assertions.assertEquals(userDetailsRequestJson.getString("firstName"), createdUserDetails.getFirstName(),
                "Returned user first name incorrect");
        Assertions.assertFalse(createdUserDetails.getUserId().trim().isEmpty(), "User id should not be empty");
    }

    @Test
    @DisplayName("GET /users requires JWT")
    @Order(2)
    void testGetUsers_whenMissingJWT_return403(){
        //Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity requestEntity = new HttpEntity(null, headers);

        //Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<UserRest>>() {
                });

        //Assert
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Http Status code 403 " +
                "Forbidden should have been returned");


    }

    @Test
    @DisplayName("/login works")
    @Order(3)
    void testUserLogin_whenValidCredentialsProvided_returnsJWTinAuthorizationHeader() throws JSONException {
        //Arrange
        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "oskszy@kkk.com");
        loginCredentials.put("password", "123456789");

        HttpEntity<String> request = new HttpEntity<>(loginCredentials.toString());

        //Act
        ResponseEntity response = testRestTemplate.postForEntity("/users/login", request, null);

        authorizationToken = response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0);
        //Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(authorizationToken);
        Assertions.assertNotNull(response.getHeaders().getValuesAsList("UserID").get(0));


    }

    @Test
    @DisplayName("GET /users works")
    void testGetUsers_whenValidJWTProvided_returnsUsers(){
        //Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authorizationToken);

        HttpEntity request = new HttpEntity<>(headers);

        //Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<UserRest>>() {
                });

        //Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().size() == 1);
    }
}
