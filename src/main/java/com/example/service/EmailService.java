package com.example.service;

import com.example.model.Email;
import java.util.List;
import java.util.Optional;

public interface EmailService {
    boolean sendEmail(Email email);
    List<Email> getInboxEmails(String recipient);
    List<Email> getSentEmails(String sender);
    Optional<Email> getEmailById(int emailId);
    boolean markEmailAsRead(int emailId);
    boolean deleteEmail(int emailId);
}