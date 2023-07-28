package com.oskar.service;

import com.oskar.data.UsersRepository;
import com.oskar.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String repeatPassword) {

        if(firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if(lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("User's last name is empty");
        }

        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());
        boolean isUserCreated = usersRepository.save(user);

        if(!isUserCreated) throw new UserServiceException("Could not create user");

        return user;
    }
}
