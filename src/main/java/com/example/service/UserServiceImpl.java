package com.example.service;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.model.User;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User register(User user) throws IllegalArgumentException {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (isUsernameTaken(user.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (isEmailTaken(user.getEmail())) {
            throw new IllegalArgumentException("Email already taken");
        }

        userDao.save(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        if (userDao.validateUser(username, password)) {
            return userDao.findByUsername(username);
        }
        return null;
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.delete(id);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userDao.findByUsername(username) != null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userDao.findByEmail(email) != null;
    }
}