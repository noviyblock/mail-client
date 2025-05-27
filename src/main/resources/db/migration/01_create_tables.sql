-- Таблица пользователей
CREATE TABLE IF NOT EXISTS users (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     username TEXT NOT NULL UNIQUE,
                                     password TEXT NOT NULL,
                                     email TEXT NOT NULL UNIQUE,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица писем
CREATE TABLE IF NOT EXISTS emails (
                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      sender TEXT NOT NULL,
                                      recipient TEXT NOT NULL,
                                      subject TEXT NOT NULL,
                                      content TEXT NOT NULL,
                                      sent_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      is_read BOOLEAN DEFAULT 0,
                                      FOREIGN KEY (sender) REFERENCES users(email),
                                      FOREIGN KEY (recipient) REFERENCES users(email)
);

-- Индексы для улучшения производительности
CREATE INDEX IF NOT EXISTS idx_emails_recipient ON emails(recipient);
CREATE INDEX IF NOT EXISTS idx_emails_sender ON emails(sender);