-- Тестовые пользователи
INSERT OR IGNORE INTO users (username, password, email) VALUES
                                                            ('user1', 'password1', 'user1@example.com'),
                                                            ('user2', 'password2', 'user2@example.com'),
                                                            ('user3', 'password3', 'user3@example.com');

-- Тестовые письма
INSERT OR IGNORE INTO emails (sender, recipient, subject, content) VALUES
                                                                       ('user1@example.com', 'user2@example.com', 'Приветствие', 'Привет, как дела?'),
                                                                       ('user2@example.com', 'user1@example.com', 'Ответ на приветствие', 'Всё отлично, спасибо!'),
                                                                       ('user3@example.com', 'user1@example.com', 'Встреча', 'Напоминаю о встрече завтра');