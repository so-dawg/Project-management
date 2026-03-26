-- Fake data for project_management database
USE project_management;

-- Clear existing data (optional)
DELETE FROM task_comments;
DELETE FROM tasks;
DELETE FROM projects;
DELETE FROM users;

-- Reset auto increment
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE projects AUTO_INCREMENT = 1;
ALTER TABLE tasks AUTO_INCREMENT = 1;
ALTER TABLE task_comments AUTO_INCREMENT = 1;

-- ==================== USERS ====================
-- Owners
INSERT INTO users (first_name, last_name, email, password) VALUES
('Sarah', 'Johnson', 'sarah.johnson@company.com', 'Owner123@'),
('Michael', 'Chen', 'michael.chen@company.com', 'Owner123@');

-- Members (Developers, Designers, etc.)
INSERT INTO users (first_name, last_name, email, password) VALUES
('John', 'Doe', 'john.doe@company.com', 'Member123@'),
('Emily', 'Smith', 'emily.smith@company.com', 'Member123@'),
('David', 'Wilson', 'david.wilson@company.com', 'Member123@'),
('Lisa', 'Brown', 'lisa.brown@company.com', 'Member123@'),
('James', 'Taylor', 'james.taylor@company.com', 'Member123@'),
('Anna', 'Martinez', 'anna.martinez@company.com', 'Member123@'),
('Robert', 'Anderson', 'robert.anderson@company.com', 'Member123@'),
('Jennifer', 'Lee', 'jennifer.lee@company.com', 'Member123@');

-- ==================== PROJECTS ====================
-- Project 1: E-commerce Website (owned by Sarah)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('E-commerce Website Redesign', 0, '2026-01-15', '2026-04-15', 1);

-- Project 2: Mobile App Development (owned by Sarah)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('Mobile App - iOS & Android', 0, '2026-02-01', '2026-06-30', 1);

-- Project 3: Internal Dashboard (owned by Michael)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('Analytics Dashboard', 0, '2026-01-01', '2026-03-31', 2);

-- Project 4: API Integration (owned by Michael)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('Third-party API Integration', 0, '2026-02-15', '2026-05-15', 2);

-- Project 5: Database Migration (owned by Sarah)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('Cloud Database Migration', 0, '2026-03-01', '2026-04-30', 1);

-- ==================== TASKS ====================
-- Tasks for Project 1 (E-commerce Website)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(1, 3, 'Design homepage mockup', 'done', 'high', '2026-02-01'),
(1, 4, 'Create product catalog page', 'in_progress', 'high', '2026-02-15'),
(1, 5, 'Implement shopping cart', 'in_progress', 'high', '2026-03-01'),
(1, 3, 'Design checkout flow', 'done', 'medium', '2026-02-10'),
(1, 6, 'Setup payment gateway', 'todo', 'high', '2026-03-15'),
(1, 7, 'Write unit tests', 'todo', 'medium', '2026-03-20'),
(1, 8, 'Performance optimization', 'todo', 'low', '2026-04-01'),
(1, 4, 'Mobile responsive design', 'in_progress', 'high', '2026-02-20');

-- Tasks for Project 2 (Mobile App)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(2, 5, 'Setup React Native project', 'done', 'high', '2026-02-10'),
(2, 6, 'Design app UI components', 'in_progress', 'high', '2026-02-28'),
(2, 7, 'Implement user authentication', 'todo', 'high', '2026-03-15'),
(2, 8, 'Create navigation system', 'todo', 'medium', '2026-03-20'),
(2, 5, 'Integrate push notifications', 'todo', 'medium', '2026-04-01'),
(2, 6, 'Build profile screen', 'todo', 'low', '2026-04-15'),
(2, 7, 'App store deployment', 'todo', 'high', '2026-06-01');

-- Tasks for Project 3 (Analytics Dashboard)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(3, 3, 'Design dashboard layout', 'done', 'high', '2026-01-15'),
(3, 4, 'Create chart components', 'done', 'high', '2026-02-01'),
(3, 5, 'Implement real-time data feed', 'in_progress', 'high', '2026-02-15'),
(3, 6, 'Add export to PDF feature', 'todo', 'medium', '2026-03-01'),
(3, 7, 'User access control', 'todo', 'high', '2026-02-20'),
(3, 8, 'Dark mode theme', 'todo', 'low', '2026-03-15');

-- Tasks for Project 4 (API Integration)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(4, 7, 'Research third-party APIs', 'done', 'medium', '2026-02-20'),
(4, 8, 'Create API wrapper classes', 'in_progress', 'high', '2026-03-10'),
(4, 3, 'Implement error handling', 'todo', 'high', '2026-03-20'),
(4, 4, 'Write API documentation', 'todo', 'medium', '2026-04-01'),
(4, 5, 'Load testing', 'todo', 'medium', '2026-04-15'),
(4, 6, 'Security audit', 'todo', 'high', '2026-05-01');

-- Tasks for Project 5 (Database Migration)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(5, 7, 'Backup current database', 'done', 'high', '2026-03-05'),
(5, 8, 'Setup cloud database instance', 'in_progress', 'high', '2026-03-15'),
(5, 3, 'Migrate user data', 'todo', 'high', '2026-03-25'),
(5, 4, 'Migrate transaction data', 'todo', 'high', '2026-04-01'),
(5, 5, 'Update connection strings', 'todo', 'medium', '2026-04-10'),
(5, 6, 'Performance testing', 'todo', 'high', '2026-04-20'),
(5, 7, 'Rollback plan documentation', 'todo', 'medium', '2026-03-30');

-- ==================== TASK COMMENTS ====================
-- Comments for task 1
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(1, 1, 'Great work on the initial design!'),
(1, 3, 'Thanks! I will share the Figma file soon.'),
(1, 4, 'Can we see the design? I need it for the product page.');

-- Comments for task 3
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(3, 1, 'This is a critical feature. Please prioritize it.'),
(3, 5, 'I am working on it. Need clarification on payment providers.');

-- Comments for task 5 (Project 2)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(5, 2, 'Which push notification service are we using?'),
(5, 5, 'Planning to use Firebase Cloud Messaging.');

-- Comments for task 10 (Real-time data)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(10, 1, 'Real-time is essential for this dashboard.'),
(10, 5, 'Using WebSocket for live updates. Making good progress!');

-- Comments for task 15 (API Documentation)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(15, 2, 'Please use Swagger for documentation.'),
(15, 4, 'Sure, I will set up Swagger UI.');

-- ==================== UPDATE PROJECT TASK COUNTS ====================
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 1;
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 2;
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 3;
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 4;
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 5;

-- ==================== VERIFY DATA ====================
SELECT '=== USERS ===' AS '';
SELECT user_id, CONCAT(first_name, ' ', last_name) AS name, email FROM users;

SELECT '=== PROJECTS ===' AS '';
SELECT project_id, pname, ntask, start_date, end_date, owner_id FROM projects;

SELECT '=== TASKS BY PROJECT ===' AS '';
SELECT p.pname, t.title, t.status, t.priority, t.due_date, 
       CONCAT(u.first_name, ' ', u.last_name) AS assigned_to
FROM tasks t
JOIN projects p ON t.projects_id = p.project_id
JOIN users u ON t.assigned_to = u.user_id
ORDER BY p.pname, t.due_date;

SELECT '=== TASK STATISTICS ===' AS '';
SELECT status, COUNT(*) AS count FROM tasks GROUP BY status;

SELECT '=== COMMENTS ===' AS '';
SELECT tc.comment_id, t.title, CONCAT(u.first_name, ' ', u.last_name) AS commenter, tc.comment
FROM task_comments tc
JOIN tasks t ON tc.task_id = t.task_id
JOIN users u ON tc.user_id = u.user_id;
