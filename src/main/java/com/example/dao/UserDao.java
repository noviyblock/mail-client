package com.example.dao;

import com.example.model.User;

public interface UserDao {
    User findById(int id);
    User findByUsername(String username);
    User findByEmail(String email);
    void save(User user);
    void update(User user);
    void delete(int id);
    boolean validateUser(String username, String password);
}