package com.example.service;

import com.example.dao.EmailDao;
import com.example.dao.EmailDaoImpl;
import com.example.model.Email;

import java.util.List;
import java.util.Optional;

public class EmailServiceImpl implements EmailService {
    private final EmailDao emailDao = new EmailDaoImpl();

    @Override
    public boolean sendEmail(Email email) {
        return emailDao.saveEmail(email);
    }

    @Override
    public List<Email> getInboxEmails(String recipient) {
        return emailDao.findInboxEmails(recipient);
    }

    @Override
    public List<Email> getSentEmails(String sender) {
        return emailDao.findSentEmails(sender);
    }

    @Override
    public Optional<Email> getEmailById(int emailId) {
        Optional<Email> emailOpt = emailDao.findEmailById(emailId);
        if (emailOpt.isPresent() && !emailOpt.get().isRead()) {
            emailDao.markAsRead(emailId);
        }
        return emailOpt;
    }

    @Override
    public boolean markEmailAsRead(int emailId) {
        return emailDao.markAsRead(emailId);
    }

    @Override
    public boolean deleteEmail(int emailId) {
        return emailDao.deleteEmail(emailId);
    }
}