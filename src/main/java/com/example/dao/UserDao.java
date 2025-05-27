package com.example.dao;

import com.example.model.User;
import java.util.Optional;

public interface UserDao {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    boolean saveUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int userId);
}