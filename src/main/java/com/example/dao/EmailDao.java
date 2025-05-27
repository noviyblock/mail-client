package com.example.dao;

import com.example.model.Email;
import java.util.List;
import java.util.Optional;

public interface EmailDao {
    boolean saveEmail(Email email);
    List<Email> findInboxEmails(String recipient);
    List<Email> findSentEmails(String sender);
    Optional<Email> findEmailById(int emailId);
    boolean markAsRead(int emailId);
    boolean deleteEmail(int emailId);
}