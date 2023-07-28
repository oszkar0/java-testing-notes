package com.oskar.service;

import com.oskar.model.User;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String password,
                    String repeatedPassword);
}
