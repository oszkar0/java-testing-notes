package com.oskar.data;

import com.oskar.model.User;

public interface UsersRepository {
    boolean save(User user);
}
