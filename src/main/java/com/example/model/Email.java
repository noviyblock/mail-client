package com.example.model;

import java.util.Date;
import java.util.Objects;

public class Email {
    private int id;
    private String sender;
    private String recipient;
    private String subject;
    private String content;
    private Date sentDate;
    private boolean isRead;

    public Email() {}

    public Email(String sender, String recipient, String subject, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.sentDate = new Date();
        this.isRead = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return id == email.id &&
                isRead == email.isRead &&
                Objects.equals(sender, email.sender) &&
                Objects.equals(recipient, email.recipient) &&
                Objects.equals(subject, email.subject) &&
                Objects.equals(content, email.content) &&
                Objects.equals(sentDate, email.sentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, recipient, subject, content, sentDate, isRead);
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", sentDate=" + sentDate +
                ", isRead=" + isRead +
                '}';
    }
}