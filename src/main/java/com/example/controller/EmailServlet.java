package com.example.controller;

import com.example.model.Email;
import com.example.model.User;
import com.example.service.EmailService;
import com.example.service.EmailServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class EmailServlet extends HttpServlet {
    private final EmailService emailService = new EmailServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = req.getPathInfo();
        if (action == null) action = "";

        try {
            switch (action) {
                case "/inbox":
                    showInbox(req, resp, user);
                    break;
                case "/sent":
                    showSentEmails(req, resp, user);
                    break;
                case "/compose":
                    showComposeForm(req, resp);
                    break;
                case "/view":
                    showEmail(req, resp, user);
                    break;
                case "/delete":
                    deleteEmail(req, resp, user);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            handleError(req, resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = req.getPathInfo();
        if (action == null) action = "";

        try {
            switch (action) {
                case "/send":
                    sendEmail(req, resp, user);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            handleError(req, resp, e);
        }
    }

    private void showInbox(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {

        List<Email> emails = emailService.getInbox(user.getEmail());
        req.setAttribute("emails", emails);
        req.getRequestDispatcher("/WEB-INF/views/email/inbox.jsp").forward(req, resp);
    }

    private void showSentEmails(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {

        List<Email> emails = emailService.getSentEmails(user.getEmail());
        req.setAttribute("emails", emails);
        req.getRequestDispatcher("/WEB-INF/views/email/sent.jsp").forward(req, resp);
    }

    private void showComposeForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/email/compose.jsp").forward(req, resp);
    }

    private void showEmail(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing email ID");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Email email = emailService.findById(id);

            if (email == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Email not found");
                return;
            }

            if (!email.getRecipient().equals(user.getEmail()) &&
                    !email.getSender().equals(user.getEmail())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to view this email");
                return;
            }

            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/email/view.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid email ID");
        }
    }

    private void deleteEmail(HttpServletRequest req, HttpServletResponse resp, User user)
            throws IOException, ServletException {

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing email ID");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Email email = emailService.findById(id);

            if (email == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Email not found");
                return;
            }

            if (!email.getRecipient().equals(user.getEmail())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You can only delete emails from your inbox");
                return;
            }

            emailService.deleteEmail(id);
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid email ID");
        }
    }

    private void sendEmail(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {

        String recipient = req.getParameter("recipient");
        String subject = req.getParameter("subject");
        String content = req.getParameter("content");

        Email email = new Email(user.getEmail(), recipient, subject, content);

        try {
            emailService.sendEmail(email);
            req.setAttribute("success", "Email sent successfully");
            showComposeForm(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", email);
            showComposeForm(req, resp);
        }
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, Exception e)
            throws ServletException, IOException {

        e.printStackTrace();
        req.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
        req.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(req, resp);
    }
}