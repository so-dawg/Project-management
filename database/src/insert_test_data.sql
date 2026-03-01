-- Test data for project_management database
USE project_management;

-- Insert an Owner
INSERT INTO users (first_name, last_name, email, password, role)
VALUES ('Admin', 'User', 'admin@example.com', 'Password123@#', 'owner');

-- Insert Members
INSERT INTO users (first_name, last_name, email, password, role)
VALUES 
    ('John', 'Doe', 'john@example.com', 'Password1!', 'member'),
    ('Alice', 'Smith', 'alice@example.com', 'Password2!', 'member'),
    ('Bob', 'Wilson', 'bob@example.com', 'Password3!', 'member');

-- Verify users
SELECT * FROM users;

-- Insert a project (owned by admin, user_id=1)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id)
VALUES ('Website Redesign', 0, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 30 DAY), 1);

-- Verify projects
SELECT * FROM projects;
