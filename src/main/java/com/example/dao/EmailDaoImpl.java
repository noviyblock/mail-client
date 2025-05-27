package com.example.dao;

import com.example.model.Email;
import com.example.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmailDaoImpl implements EmailDao {
    @Override
    public boolean saveEmail(Email email) {
        String sql = "INSERT INTO emails(sender, recipient, subject, content) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email.getSender());
            pstmt.setString(2, email.getRecipient());
            pstmt.setString(3, email.getSubject());
            pstmt.setString(4, email.getContent());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Email> findInboxEmails(String recipient) {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM emails WHERE recipient = ? ORDER BY sent_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, recipient);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Email email = new Email();
                email.setId(rs.getInt("id"));
                email.setSender(rs.getString("sender"));
                email.setRecipient(rs.getString("recipient"));
                email.setSubject(rs.getString("subject"));
                email.setContent(rs.getString("content"));
                email.setSentDate(rs.getTimestamp("sent_date"));
                email.setRead(rs.getBoolean("is_read"));
                emails.add(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    @Override
    public List<Email> findSentEmails(String sender) {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM emails WHERE sender = ? ORDER BY sent_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sender);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Email email = new Email();
                email.setId(rs.getInt("id"));
                email.setSender(rs.getString("sender"));
                email.setRecipient(rs.getString("recipient"));
                email.setSubject(rs.getString("subject"));
                email.setContent(rs.getString("content"));
                email.setSentDate(rs.getTimestamp("sent_date"));
                email.setRead(rs.getBoolean("is_read"));
                emails.add(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    @Override
    public Optional<Email> findEmailById(int emailId) {
        String sql = "SELECT * FROM emails WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, emailId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Email email = new Email();
                email.setId(rs.getInt("id"));
                email.setSender(rs.getString("sender"));
                email.setRecipient(rs.getString("recipient"));
                email.setSubject(rs.getString("subject"));
                email.setContent(rs.getString("content"));
                email.setSentDate(rs.getTimestamp("sent_date"));
                email.setRead(rs.getBoolean("is_read"));
                return Optional.of(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean markAsRead(int emailId) {
        String sql = "UPDATE emails SET is_read = TRUE WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, emailId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEmail(int emailId) {
        String sql = "DELETE FROM emails WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, emailId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}