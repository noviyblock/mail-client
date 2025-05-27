package com.example.service;

import com.example.model.User;

public interface UserService {
    User register(User user) throws IllegalArgumentException;
    User login(String username, String password);
    User findById(int id);
    User findByUsername(String username);
    User findByEmail(String email);
    void updateUser(User user);
    void deleteUser(int id);
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
}