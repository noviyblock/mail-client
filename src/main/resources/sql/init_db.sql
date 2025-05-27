-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     username TEXT NOT NULL UNIQUE,
                                     password TEXT NOT NULL,
                                     email TEXT NOT NULL UNIQUE
);

-- Создание таблицы писем
CREATE TABLE IF NOT EXISTS emails (
                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      sender TEXT NOT NULL,
                                      recipient TEXT NOT NULL,
                                      subject TEXT,
                                      content TEXT,
                                      sent_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      is_read BOOLEAN DEFAULT FALSE,
                                      FOREIGN KEY(sender) REFERENCES users(email),
                                      FOREIGN KEY(recipient) REFERENCES users(email)
);

-- Вставка тестовых данных (опционально)
INSERT OR IGNORE INTO users (username, password, email) VALUES
                                                            ('user1', 'password1', 'user1@example.com'),
                                                            ('user2', 'password2', 'user2@example.com'),
                                                            ('user3', 'password3', 'user3@example.com');

INSERT OR IGNORE INTO emails (sender, recipient, subject, content) VALUES
                                                                       ('user1@example.com', 'user2@example.com', 'Test Subject 1', 'This is a test email content 1'),
                                                                       ('user2@example.com', 'user1@example.com', 'Test Subject 2', 'This is a test email content 2'),
                                                                       ('user3@example.com', 'user1@example.com', 'Test Subject 3', 'This is a test email content 3');