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

@WebServlet("/email/*")
public class EmailServlet extends HttpServlet {
    private final EmailService emailService = new EmailServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = req.getPathInfo();

        if (action == null || action.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
            return;
        }

        switch (action) {
            case "/inbox":
                List<Email> inboxEmails = emailService.getInboxEmails(user.getEmail());
                req.setAttribute("emails", inboxEmails);
                req.setAttribute("folder", "inbox");
                req.getRequestDispatcher("/WEB-INF/views/email/inbox.jsp").forward(req, resp);
                break;
            case "/sent":
                List<Email> sentEmails = emailService.getSentEmails(user.getEmail());
                req.setAttribute("emails", sentEmails);
                req.setAttribute("folder", "sent");
                req.getRequestDispatcher("/WEB-INF/views/email/inbox.jsp").forward(req, resp);
                break;
            case "/compose":
                req.getRequestDispatcher("/WEB-INF/views/email/compose.jsp").forward(req, resp);
                break;
            case "/view":
                String emailIdParam = req.getParameter("id");
                if (emailIdParam == null || emailIdParam.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/email/inbox");
                    return;
                }

                try {
                    int emailId = Integer.parseInt(emailIdParam);
                    emailService.getEmailById(emailId).ifPresentOrElse(
                            email -> {
                                try {
                                    req.setAttribute("email", email);
                                    req.getRequestDispatcher("/WEB-INF/views/email/view.jsp").forward(req, resp);
                                } catch (ServletException | IOException e) {
                                    e.printStackTrace();
                                    try {
                                        resp.sendRedirect(req.getContextPath() + "/email/inbox");
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            },
                            () -> {
                                try {
                                    resp.sendRedirect(req.getContextPath() + "/email/inbox");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/email/inbox");
                }
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = req.getPathInfo();

        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
            return;
        }

        switch (action) {
            case "/send":
                handleSendEmail(req, resp, user);
                break;
            case "/delete":
                handleDeleteEmail(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleSendEmail(HttpServletRequest req, HttpServletResponse resp, User sender) throws IOException {
        String recipient = req.getParameter("recipient");
        String subject = req.getParameter("subject");
        String content = req.getParameter("content");

        Email email = new Email();
        email.setSender(sender.getEmail());
        email.setRecipient(recipient);
        email.setSubject(subject);
        email.setContent(content);

        if (emailService.sendEmail(email)) {
            req.getSession().setAttribute("message", "Email sent successfully");
        } else {
            req.getSession().setAttribute("error", "Failed to send email");
        }

        resp.sendRedirect(req.getContextPath() + "/email/sent");
    }

    private void handleDeleteEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String emailIdParam = req.getParameter("id");
        String folder = req.getParameter("folder");

        if (emailIdParam == null || emailIdParam.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
            return;
        }

        try {
            int emailId = Integer.parseInt(emailIdParam);
            if (emailService.deleteEmail(emailId)) {
                req.getSession().setAttribute("message", "Email deleted successfully");
            } else {
                req.getSession().setAttribute("error", "Failed to delete email");
            }
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("error", "Invalid email ID");
        }

        if ("sent".equals(folder)) {
            resp.sendRedirect(req.getContextPath() + "/email/sent");
        } else {
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
        }
    }
}