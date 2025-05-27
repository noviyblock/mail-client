package com.example.service;

import com.example.model.Email;
import java.util.List;

public interface EmailService {
    Email findById(int id);
    List<Email> getInbox(String recipient);
    List<Email> getSentEmails(String sender);
    void sendEmail(Email email);
    void deleteEmail(int id);
    void markAsRead(int id);
}