-- Create database (ignore error if exists)
CREATE DATABASE IF NOT EXISTS project_management;

-- Use the database
USE project_management;

-- ============================================
-- TABLES
-- ============================================

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    username VARCHAR(100) UNIQUE,
    email VARCHAR(250) UNIQUE NOT NULL,
    password VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Projects table
CREATE TABLE IF NOT EXISTS projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    pname VARCHAR(250) NOT NULL,
    description TEXT,
    ntask INT DEFAULT 0,
    start_date DATE,
    end_date DATE,
    owner_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users(user_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Project Members table (Many-to-Many relationship)
CREATE TABLE IF NOT EXISTS project_members (
    project_id INT NOT NULL,
    user_id INT NOT NULL,
    joined_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (project_id, user_id),
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tasks table
CREATE TABLE IF NOT EXISTS tasks (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    projects_id INT NOT NULL,
    assigned_to INT,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    status ENUM('todo', 'in_progress', 'done') DEFAULT 'todo',
    priority ENUM('low', 'medium', 'high', 'urgent') DEFAULT 'medium',
    due_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(projects_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY(assigned_to) REFERENCES users(user_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- TEST DATA
-- ============================================

-- Insert test users (skip if username exists)
INSERT INTO users (first_name, last_name, username, email, password) VALUES
    ('Admin', 'User', 'admin', 'admin@example.com', 'Password123!'),
    ('John', 'Doe', 'john', 'john@example.com', 'Password1!'),
    ('Alice', 'Smith', 'alice', 'alice@example.com', 'Password2!'),
    ('Bob', 'Wilson', 'bob', 'bob@example.com', 'Password3!'),
    ('Sarah', 'Johnson', 'sarah.johnson', 'sarah.johnson@company.com', 'Owner123@'),
    ('Michael', 'Chen', 'michael.chen', 'michael.chen@company.com', 'Owner123@'),
    ('Emily', 'Smith', 'emily.smith', 'emily.smith@company.com', 'Member123@'),
    ('David', 'Wilson', 'david.wilson', 'david.wilson@company.com', 'Member123@'),
    ('Lisa', 'Brown', 'lisa.brown', 'lisa.brown@company.com', 'Member123@'),
    ('James', 'Taylor', 'james.taylor', 'james.taylor@company.com', 'Member123@')
ON DUPLICATE KEY UPDATE email=VALUES(email);

-- Insert test projects
INSERT INTO projects (pname, description, owner_id, start_date, end_date) VALUES
    ('E-commerce Website', 'Online shopping platform', 5, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 90 DAY)),
    ('Mobile App', 'iOS and Android application', 5, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 120 DAY)),
    ('Analytics Dashboard', 'Business intelligence dashboard', 6, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 60 DAY)),
    ('API Integration', 'Third-party API connections', 6, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 45 DAY)),
    ('Cloud Migration', 'Database migration to cloud', 5, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 30 DAY))
ON DUPLICATE KEY UPDATE pname=VALUES(pname);

-- Insert test tasks
INSERT INTO tasks (projects_id, assigned_to, title, description, priority, due_date, status) VALUES
    (1, 7, 'Design homepage', 'Create homepage mockup', 'high', DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'todo'),
    (1, 8, 'Setup database', 'Design and create database schema', 'high', DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'in_progress'),
    (1, 9, 'Payment gateway', 'Integrate Stripe payment', 'high', DATE_ADD(CURDATE(), INTERVAL 21 DAY), 'todo'),
    (2, 7, 'App architecture', 'Setup React Native project', 'high', DATE_ADD(CURDATE(), INTERVAL 10 DAY), 'todo'),
    (2, 8, 'UI components', 'Create reusable components', 'medium', DATE_ADD(CURDATE(), INTERVAL 20 DAY), 'todo'),
    (3, 9, 'Dashboard layout', 'Design main dashboard', 'high', DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'in_progress'),
    (3, 10, 'Chart components', 'Create data visualization', 'medium', DATE_ADD(CURDATE(), INTERVAL 21 DAY), 'todo'),
    (4, 7, 'API research', 'Evaluate available APIs', 'medium', DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'done'),
    (4, 8, 'Error handling', 'Implement retry logic', 'high', DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'todo'),
    (5, 9, 'Backup database', 'Full backup before migration', 'high', DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'done');

-- Insert project members
INSERT INTO project_members (project_id, user_id) VALUES
    (1, 7), (1, 8), (1, 9),
    (2, 7), (2, 8),
    (3, 9), (3, 10),
    (4, 7), (4, 8),
    (5, 9)
ON DUPLICATE KEY UPDATE project_id=VALUES(project_id);

-- ============================================
-- VERIFICATION
-- ============================================

SELECT '========================================' AS '';
SELECT 'DATABASE SETUP COMPLETE!' AS '';
SELECT '========================================' AS '';

SELECT 'Tables created:' AS '';
SHOW TABLES;

SELECT '' AS '';
SELECT 'Test users created:' AS '';
SELECT user_id, username, CONCAT(first_name, ' ', last_name) AS name, email FROM users;

SELECT '' AS '';
SELECT 'Projects created:' AS '';
SELECT project_id, pname, owner_id FROM projects;

SELECT '' AS '';
SELECT 'Tasks created:' AS '';
SELECT task_id, title, priority, status FROM tasks;

SELECT '' AS '';
SELECT '========================================' AS '';
SELECT 'LOGIN CREDENTIALS:' AS '';
SELECT '========================================' AS '';
SELECT 'Email: admin@example.com | Password: Password123!' AS 'Test Accounts'
UNION ALL SELECT 'Email: john@example.com | Password: Password1!'
UNION ALL SELECT 'Email: sarah.johnson@company.com | Password: Owner123@'
UNION ALL SELECT 'Email: michael.chen@company.com | Password: Owner123@';

SELECT '' AS '';
SELECT 'To run the application:' AS '';
SELECT '  Windows: java -cp ".;database\\mariadb-java-client-3.1.4.jar" Main' AS '';
SELECT '  Linux:   java -cp .:database/mariadb-java-client-3.1.4.jar Main' AS '';
SELECT '========================================' AS '';
