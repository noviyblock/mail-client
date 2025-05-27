package com.example.dao;

import com.example.model.Email;
import com.example.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDaoImpl implements EmailDao {
    @Override
    public Email findById(int id) {
        String sql = "SELECT * FROM emails WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractEmailFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Email> findByRecipient(String recipient) {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM emails WHERE recipient = ? ORDER BY sent_date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recipient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                emails.add(extractEmailFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    @Override
    public List<Email> findBySender(String sender) {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM emails WHERE sender = ? ORDER BY sent_date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sender);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                emails.add(extractEmailFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    @Override
    public void save(Email email) {
        String sql = "INSERT INTO emails (sender, recipient, subject, content, sent_date, is_read) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, email.getSender());
            stmt.setString(2, email.getRecipient());
            stmt.setString(3, email.getSubject());
            stmt.setString(4, email.getContent());
            stmt.setTimestamp(5, new Timestamp(email.getSentDate().getTime()));
            stmt.setBoolean(6, email.isRead());
            stmt.executeUpdate();

            // Get the auto-generated id
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    email.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Email email) {
        String sql = "UPDATE emails SET sender = ?, recipient = ?, subject = ?, content = ?, " +
                "is_read = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.getSender());
            stmt.setString(2, email.getRecipient());
            stmt.setString(3, email.getSubject());
            stmt.setString(4, email.getContent());
            stmt.setBoolean(5, email.isRead());
            stmt.setInt(6, email.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM emails WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void markAsRead(int id) {
        String sql = "UPDATE emails SET is_read = true WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Email extractEmailFromResultSet(ResultSet rs) throws SQLException {
        Email email = new Email();
        email.setId(rs.getInt("id"));
        email.setSender(rs.getString("sender"));
        email.setRecipient(rs.getString("recipient"));
        email.setSubject(rs.getString("subject"));
        email.setContent(rs.getString("content"));
        email.setSentDate(new Date(rs.getTimestamp("sent_date").getTime()));
        email.setRead(rs.getBoolean("is_read"));
        return email;
    }
}