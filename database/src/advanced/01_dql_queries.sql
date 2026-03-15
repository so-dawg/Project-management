-- Simple SELECT queries for project management database

USE project_management;

-- ------------------------------------------
-- Query 1: Show all tasks with project name
-- ------------------------------------------
SELECT 
    t.task_id,
    t.title,
    t.status,
    t.priority,
    p.pname AS project_name
FROM tasks t
JOIN projects p ON t.projects_id = p.project_id
ORDER BY t.task_id;

-- ------------------------------------------
-- Query 2: Show tasks with assigned user
-- ------------------------------------------
SELECT 
    t.task_id,
    t.title,
    u.first_name,
    u.last_name,
    t.status
FROM tasks t
JOIN users u ON t.assigned_to = u.user_id
ORDER BY t.task_id;

-- ------------------------------------------
-- Query 3: Count tasks by status
-- ------------------------------------------
SELECT 
    status,
    COUNT(*) AS total
FROM tasks
GROUP BY status;

-- ------------------------------------------
-- Query 4: Count tasks by priority
-- ------------------------------------------
SELECT 
    priority,
    COUNT(*) AS total
FROM tasks
GROUP BY priority;

-- ------------------------------------------
-- Query 5: Show projects with owner name
-- ------------------------------------------
SELECT 
    p.project_id,
    p.pname,
    u.first_name,
    u.last_name
FROM projects p
JOIN users u ON p.owner_id = u.user_id
ORDER BY p.project_id;

-- ------------------------------------------
-- Query 6: Find high priority tasks
-- ------------------------------------------
SELECT 
    task_id,
    title,
    priority,
    due_date
FROM tasks
WHERE priority = 'high'
ORDER BY due_date;

-- ------------------------------------------
-- Query 7: Find overdue tasks
-- ------------------------------------------
SELECT 
    task_id,
    title,
    due_date,
    status
FROM tasks
WHERE due_date < CURDATE() 
  AND status != 'done'
ORDER BY due_date;

-- ------------------------------------------
-- Query 8: Show all members in a project
-- ------------------------------------------
SELECT 
    p.pname,
    u.first_name,
    u.last_name,
    u.email
FROM projects p
JOIN users u ON p.owner_id = u.user_id
WHERE p.project_id = 1;

-- ------------------------------------------
-- Query 9: Task count per project
-- ------------------------------------------
SELECT 
    p.pname,
    COUNT(t.task_id) AS task_count
FROM projects p
LEFT JOIN tasks t ON p.project_id = t.projects_id
GROUP BY p.pname;

-- ------------------------------------------
-- Query 10: Users and their task count
-- ------------------------------------------
SELECT 
    u.first_name,
    u.last_name,
    COUNT(t.task_id) AS tasks_assigned
FROM users u
LEFT JOIN tasks t ON u.user_id = t.assigned_to
GROUP BY u.first_name, u.last_name
ORDER BY tasks_assigned DESC;
