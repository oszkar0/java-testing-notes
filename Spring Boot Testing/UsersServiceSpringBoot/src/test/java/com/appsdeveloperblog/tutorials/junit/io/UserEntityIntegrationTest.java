package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;

@DataJpaTest
public class UserEntityIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Test
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnStoredUserDetails(){
        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID().toString());
        user.setFirstName("Oskar");
        user.setLastName("Szysiak");
        user.setEmail("oskszy@kkk.com");
        user.setEncryptedPassword("123456789");

        UserEntity storedUser = testEntityManager.persistAndFlush(user);

        Assertions.assertTrue(user.getId() > -1);
        Assertions.assertEquals(user.getUserId(), storedUser.getUserId());
        //...
    }
}
