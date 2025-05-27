package com.example.service;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.model.User;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public boolean registerUser(User user) {
        // Check if username or email already exists
        if (userDao.findUserByUsername(user.getUsername()).isPresent() ||
                userDao.findUserByEmail(user.getEmail()).isPresent()) {
            return false;
        }
        return userDao.saveUser(user);
    }

    @Override
    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = userDao.findUserByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    public boolean updateUserProfile(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean deleteUser(int userId) {
        return userDao.deleteUser(userId);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
}