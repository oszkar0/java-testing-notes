package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;

@DataJpaTest
public class UsersRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UsersRepository usersRepository;
    @Test
    void testFindBYEmail_whenGIvenCorrectEmail_returnUserEntity(){
        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID().toString());
        user.setFirstName("Oskar");
        user.setLastName("Szysiak");
        user.setEmail("oskszy@kkk.com");
        user.setEncryptedPassword("123456789");
        testEntityManager.persistAndFlush(user);

        UserEntity storedUser = usersRepository.findByEmail(user.getEmail());

        Assertions.assertEquals(user.getEmail(), storedUser.getEmail());
    }
}
