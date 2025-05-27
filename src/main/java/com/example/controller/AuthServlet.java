package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();
        if (action == null) action = "";

        try {
            switch (action) {
                case "/login":
                    showLoginPage(req, resp);
                    break;
                case "/register":
                    showRegisterPage(req, resp);
                    break;
                case "/logout":
                    logout(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            handleError(req, resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if ("/login".equals(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            User user = userService.login(username, password);
            if (user != null) {
                // Создаем новую сессию и добавляем атрибуты
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30*60); // 30 минут

                // Перенаправляем с полным URL
                String redirectURL = req.getContextPath() + "/email/inbox.jsp";
                resp.sendRedirect(redirectURL);
                return;
            } else {
                req.setAttribute("error", "Invalid username or password");
            }
        }
        // Если авторизация не удалась, показываем форму снова
        req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);

    }

    private void showLoginPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
    }

    private void showRegisterPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
        } else {
            req.setAttribute("error", "Invalid username or password");
            showLoginPage(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = new User(
                req.getParameter("username"),
                req.getParameter("password"),
                req.getParameter("email")
        );

        try {
            userService.register(user);

            // Создаём сессию и добавляем пользователя
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            // Перенаправляем сразу в inbox после регистрации
            resp.sendRedirect(req.getContextPath() + "/email/inbox");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("user", user); // Сохраняем введённые данные для повторного заполнения формы
            showRegisterPage(req, resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/auth/login");
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, Exception e)
            throws ServletException, IOException {

        e.printStackTrace();
        req.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
        req.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(req, resp);
    }
}