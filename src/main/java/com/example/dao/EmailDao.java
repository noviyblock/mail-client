package com.example.dao;

import com.example.model.Email;
import java.util.List;

public interface EmailDao {
    Email findById(int id);
    List<Email> findByRecipient(String recipient);
    List<Email> findBySender(String sender);
    void save(Email email);
    void update(Email email);
    void delete(int id);
    void markAsRead(int id);
}