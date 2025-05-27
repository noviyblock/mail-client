package com.example.service;

import com.example.model.User;

import java.util.Optional;

public interface UserService {
    boolean registerUser(User user);
    Optional<User> authenticate(String username, String password);
    boolean updateUserProfile(User user);
    boolean deleteUser(int userId);
    Optional<User> getUserByEmail(String email);
}