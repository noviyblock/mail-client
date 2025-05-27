package com.example.service;

import com.example.dao.EmailDao;
import com.example.dao.EmailDaoImpl;
import com.example.model.Email;

import java.util.List;

public class EmailServiceImpl implements EmailService {
    private final EmailDao emailDao = new EmailDaoImpl();

    @Override
    public Email findById(int id) {
        Email email = emailDao.findById(id);
        if (email != null && !email.isRead()) {
            emailDao.markAsRead(id);
            email.setRead(true);
        }
        return email;
    }

    @Override
    public List<Email> getInbox(String recipient) {
        return emailDao.findByRecipient(recipient);
    }

    @Override
    public List<Email> getSentEmails(String sender) {
        return emailDao.findBySender(sender);
    }

    @Override
    public void sendEmail(Email email) {
        if (email.getRecipient() == null || email.getRecipient().trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty");
        }
        if (email.getSubject() == null || email.getSubject().trim().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be empty");
        }
        emailDao.save(email);
    }

    @Override
    public void deleteEmail(int id) {
        emailDao.delete(id);
    }

    @Override
    public void markAsRead(int id) {
        emailDao.markAsRead(id);
    }
}