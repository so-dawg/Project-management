-- Additional fake data for project_management database
USE project_management;

-- ==================== MORE USERS ====================
-- Additional Members (Developers, Designers, QA, etc.)
INSERT INTO users (first_name, last_name, email, password) VALUES
('Chris', 'Garcia', 'chris.garcia@company.com', 'Member123@'),
('Michelle', 'White', 'michelle.white@company.com', 'Member123@'),
('Kevin', 'Harris', 'kevin.harris@company.com', 'Member123@'),
('Rachel', 'Clark', 'rachel.clark@company.com', 'Member123@'),
('Daniel', 'Lewis', 'daniel.lewis@company.com', 'Member123@'),
('Sophia', 'Walker', 'sophia.walker@company.com', 'Member123@'),
('Matthew', 'Hall', 'matthew.hall@company.com', 'Member123@'),
('Olivia', 'Young', 'olivia.young@company.com', 'Member123@');

-- ==================== MORE PROJECTS ====================
-- Project 6: Customer Portal (owned by Michael)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('Customer Support Portal', 0, '2026-03-01', '2026-05-31', 2);

-- Project 7: Marketing Website (owned by Sarah)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('Marketing Website Refresh', 0, '2026-04-01', '2026-06-30', 1);

-- Project 8: DevOps Pipeline (owned by Michael)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('CI/CD Pipeline Setup', 0, '2026-03-15', '2026-05-15', 2);

-- Project 9: Security Audit (owned by Sarah)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('Security Compliance Audit', 0, '2026-04-15', '2026-07-15', 1);

-- Project 10: Data Analytics Platform (owned by Michael)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('Big Data Analytics Platform', 0, '2026-05-01', '2026-09-30', 2);

-- ==================== TASKS ====================
-- Tasks for Project 6 (Customer Support Portal)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(6, 9, 'Design ticket submission form', 'todo', 'high', '2026-03-20'),
(6, 10, 'Create knowledge base UI', 'todo', 'medium', '2026-03-25'),
(6, 11, 'Implement live chat feature', 'todo', 'high', '2026-04-10'),
(6, 12, 'Build FAQ section', 'todo', 'low', '2026-04-15'),
(6, 9, 'Setup email notifications', 'todo', 'medium', '2026-04-20'),
(6, 10, 'Admin dashboard for support team', 'todo', 'high', '2026-05-01');

-- Tasks for Project 7 (Marketing Website)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(7, 3, 'Redesign landing page', 'todo', 'high', '2026-04-15'),
(7, 4, 'Create about us page', 'todo', 'medium', '2026-04-20'),
(7, 13, 'Write marketing copy', 'todo', 'medium', '2026-04-25'),
(7, 14, 'Design contact form', 'todo', 'low', '2026-05-01'),
(7, 3, 'SEO optimization', 'todo', 'high', '2026-05-15'),
(7, 4, 'Blog section implementation', 'todo', 'medium', '2026-05-20'),
(7, 13, 'Social media integration', 'todo', 'low', '2026-06-01');

-- Tasks for Project 8 (DevOps Pipeline)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(8, 11, 'Setup Jenkins server', 'todo', 'high', '2026-03-25'),
(8, 12, 'Configure Docker containers', 'todo', 'high', '2026-04-01'),
(8, 15, 'Create automated test pipeline', 'todo', 'high', '2026-04-10'),
(8, 16, 'Setup staging environment', 'todo', 'medium', '2026-04-20'),
(8, 11, 'Configure deployment scripts', 'todo', 'high', '2026-04-30'),
(8, 12, 'Setup monitoring and alerts', 'todo', 'medium', '2026-05-10');

-- Tasks for Project 9 (Security Audit)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(9, 15, 'Vulnerability scanning', 'todo', 'high', '2026-05-01'),
(9, 16, 'Penetration testing', 'todo', 'high', '2026-05-15'),
(9, 11, 'Code security review', 'todo', 'high', '2026-05-20'),
(9, 12, 'Update security policies', 'todo', 'medium', '2026-06-01'),
(9, 15, 'GDPR compliance check', 'todo', 'high', '2026-06-15'),
(9, 16, 'Security training for team', 'todo', 'medium', '2026-07-01');

-- Tasks for Project 10 (Data Analytics Platform)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(10, 9, 'Design data warehouse schema', 'todo', 'high', '2026-05-15'),
(10, 10, 'Setup ETL pipelines', 'todo', 'high', '2026-06-01'),
(10, 11, 'Implement data visualization', 'todo', 'medium', '2026-06-15'),
(10, 12, 'Build reporting module', 'todo', 'medium', '2026-07-01'),
(10, 13, 'Machine learning integration', 'todo', 'low', '2026-08-01'),
(10, 14, 'Real-time analytics dashboard', 'todo', 'high', '2026-08-15'),
(10, 15, 'Data backup and recovery', 'todo', 'high', '2026-09-01'),
(10, 16, 'Performance optimization', 'todo', 'medium', '2026-09-15');

-- ==================== MORE TASK COMMENTS ====================
-- Comments for task 20 (Ticket submission form)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(20, 2, 'Make sure the form is user-friendly and accessible.'),
(20, 9, 'Will use React Hook Forms for validation.');

-- Comments for task 22 (Live chat feature)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(22, 1, 'Consider using WebSocket for real-time communication.'),
(22, 11, 'Planning to integrate Socket.io with Node.js backend.');

-- Comments for task 27 (Landing page redesign)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(27, 1, 'Keep the brand colors consistent with our guidelines.'),
(27, 3, 'Already have some mockups ready. Will share tomorrow.');

-- Comments for task 32 (Jenkins server)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(32, 2, 'Use the latest LTS version of Jenkins.'),
(32, 11, 'Will also configure Blue Ocean for better UI.');

-- Comments for task 37 (Vulnerability scanning)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(37, 1, 'This is critical for our compliance certification.'),
(37, 15, 'Using OWASP ZAP and Nessus for comprehensive scanning.');

-- Comments for task 42 (Data warehouse)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(42, 2, 'Consider using Snowflake or Redshift for scalability.'),
(42, 9, 'Leaning towards Snowflake. Will present options next week.');

-- Comments for task 45 (Reporting module)
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(45, 1, 'Export to Excel and CSV is a must-have.'),
(45, 12, 'Will also add PDF export option.');

-- ==================== UPDATE PROJECT TASK COUNTS ====================
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 6;
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 7;
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 8;
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 9;
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id = 10;

-- ==================== VERIFY DATA ====================
SELECT '=== TOTAL USERS ===' AS '';
SELECT COUNT(*) AS total_users FROM users;

SELECT '=== TOTAL PROJECTS ===' AS '';
SELECT COUNT(*) AS total_projects FROM projects;

SELECT '=== TOTAL TASKS ===' AS '';
SELECT COUNT(*) AS total_tasks FROM tasks;

SELECT '=== TOTAL COMMENTS ===' AS '';
SELECT COUNT(*) AS total_comments FROM task_comments;

SELECT '=== TASKS BY STATUS ===' AS '';
SELECT status, COUNT(*) AS count FROM tasks GROUP BY status;

SELECT '=== TASKS BY PRIORITY ===' AS '';
SELECT priority, COUNT(*) AS count FROM tasks GROUP BY priority;

SELECT '=== PROJECTS WITH TASK COUNTS ===' AS '';
SELECT project_id, pname, ntask, 
       CONCAT(u.first_name, ' ', u.last_name) AS owner_name
FROM projects p
JOIN users u ON p.owner_id = u.user_id
ORDER BY project_id;
